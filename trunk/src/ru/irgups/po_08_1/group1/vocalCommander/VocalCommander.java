package ru.irgups.po_08_1.group1.vocalCommander;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.ExitCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.SwitchModeCommand;
import ru.irgups.po_08_1.group1.vocalCommander.gui.AppTrayIcon;
import ru.irgups.po_08_1.group1.vocalCommander.mode.CommandMode;
import ru.irgups.po_08_1.group1.vocalCommander.mode.KeyboardMode;
import ru.irgups.po_08_1.group1.vocalCommander.mode.LockMode;
import ru.irgups.po_08_1.group1.vocalCommander.mode.Mode;
import ru.irgups.po_08_1.group1.vocalCommander.mode.MouseMode;
import ru.irgups.po_08_1.group1.vocalCommander.service.CommandExecutionService;
import ru.irgups.po_08_1.group1.vocalCommander.service.CommandRecognitionService;

import javax.swing.SwingUtilities;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class VocalCommander {
    public static final String CONFIG = "config.xml";
    public static final String USER_COMMANDS_STORE = "./model/userCommands.xml";
    public static final String USER_COMMANDS_GRAMMAR = "./model/userCommands.gram";

    private static Microphone microphone;
    private static JSGFGrammar grammar;
    private static Recognizer recognizer;

    private static ModeSwitcher switcher;
    private static BlockingQueue<Command> commandQueue;

    private static Thread recognitionThread;
    private static Thread executionThread;

    private static AppTrayIcon trayIcon;

    public static void init() {
        switcher = new ModeSwitcher();
        commandQueue = new LinkedBlockingQueue<Command>();

        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        CommandsStore userCommandsStore = new CommandsStore(
                new File(USER_COMMANDS_STORE),
                new File(USER_COMMANDS_GRAMMAR));
        List<CommandsStore.Wrapper> userCommands;
        try {
            userCommands = userCommandsStore.load(robot);
        } catch (IOException e) {
            userCommands = new LinkedList<CommandsStore.Wrapper>();

            try {
                userCommandsStore.store(userCommands);
            } catch (IOException x) {
            }
        }

        ConfigurationManager manager = new ConfigurationManager(CONFIG);
        microphone = (Microphone) manager.lookup("microphone");
        grammar = (JSGFGrammar) manager.lookup("grammar");
        recognizer = (Recognizer) manager.lookup("recognizer");

        /*
        DONE: Создать режимы и добавить в них команды для взаимодействия между друг другом
        Режимы:
            commandMode
            mouseMode
            lockMode
        Команды:
            Для commandMode:
                мышь
                блокировать
                завершить работу
            Для mouseMode:
                мышь
                команды
                блокировать
                завершить работу
            Для lockMode:
                разблокировать
        */

        Mode commandMode = new CommandMode();
        Mode mouseMode = new MouseMode(robot);
        Mode keyboardMode = new KeyboardMode(robot);
        Mode lockMode = new LockMode(switcher, grammar);

        Command switchToCommandModeCommand = new SwitchModeCommand(switcher, grammar, commandMode),
                switchToMouseModeCommand = new SwitchModeCommand(switcher, grammar, mouseMode),
                switchToKeyboardModeCommand = new SwitchModeCommand(switcher, grammar, keyboardMode),
                switchToLockModeCommand = new SwitchModeCommand(switcher, grammar, lockMode),
                exitCommand = new ExitCommand();

        commandMode.addCommand("мышь", switchToMouseModeCommand);
        commandMode.addCommand("клавиатура", switchToKeyboardModeCommand);
        commandMode.addCommand("блокировать", switchToLockModeCommand);
        commandMode.addCommand("завершить работу", exitCommand);

        mouseMode.addCommand("мышь", switchToMouseModeCommand);
        mouseMode.addCommand("клавиатура", switchToKeyboardModeCommand);
        mouseMode.addCommand("команды", switchToCommandModeCommand);
        mouseMode.addCommand("блокировать", switchToLockModeCommand);
        mouseMode.addCommand("завершить работу", exitCommand);

        keyboardMode.addCommand("мышь", switchToMouseModeCommand);
        keyboardMode.addCommand("команды", switchToCommandModeCommand);
        keyboardMode.addCommand("блокировать", switchToLockModeCommand);
        keyboardMode.addCommand("завершить работу", exitCommand);

        for (CommandsStore.Wrapper wrapper : userCommands)
            commandMode.addCommand(wrapper.cause, wrapper.effect);

        switcher.setActiveMode(commandMode);

        recognitionThread = new Thread(new CommandRecognitionService(recognizer, commandQueue, switcher), "Recognition Thread");
        executionThread = new Thread(new CommandExecutionService(commandQueue), "Execution Thread");
    }

    public static void start() {
        recognizer.allocate();
        microphone.startRecording();

        recognitionThread.start();
        executionThread.start();

        //DONE: Раскомментировать, когда AppTrayIcon будет реализован
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                trayIcon = new AppTrayIcon(commandQueue, switcher);
                try {
                    SystemTray.getSystemTray().add(trayIcon);
                } catch (AWTException e) {
                }
            }
        });

        switcher.getActiveMode().activate();
    }

    public static void stop() {
        switcher.getActiveMode().deactivate();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SystemTray.getSystemTray().remove(trayIcon);
            }
        });

        microphone.stopRecording();

        recognitionThread.interrupt();
        try {
            recognitionThread.join();
        } catch (InterruptedException e) {
        }
        executionThread.interrupt();

        try {
            recognizer.deallocate();
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayMessage(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                trayIcon.displayMessage("VocalCommander", message, TrayIcon.MessageType.INFO);
            }
        });
    }

    public static void main(String[] args) {
        try {
            if (args.length > 0) System.setOut(new PrintStream(System.out, true, args[0]));

            // Отладочный вывод
            System.out.println("Вас приветствует VocalCommander!");

            // Отладочный вывод
            System.out.println("В любой момент вам доступна голосовая команда \"помощь\" для получения справки по текущему режиму работы системы.");

            init();

            // Отладочный вывод
            System.out.println("Инициализация компонентов системы завершена.");
            System.out.println("Запуск сервисов.");

            start();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding isn't supported.");
        }
    }
}
