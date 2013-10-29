package ru.irgups.po_08_1.group1.vocalCommander.mode;

import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.HelpCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.KeyPressCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.KeyboardMoveCommand;
import ru.irgups.po_08_1.group1.vocalCommander.gui.VirtualKeyboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyboardMode extends SimpleMode {
    //DONE: Подумать и написать строку для помощи
    public static final String HELP_MESSAGE =
            "Вы находитесь в режиме голосового управления клавиатурой.\n" +
                    "Чтобы ввести символ с помощью данной вирутальной клавиатуры\n" +
                    "необходимо спозиционировать маркер на нужной клавише.\n" +
                    "Для этого используются следующие команды:\n" +
                    "    - \"выше\" - передвигает маркер на один уровень вверх;\n" +
                    "    - \"ниже\" - передвигает маркер на один уровень вниз;\n" +
                    "    - \"влево\" - передвигает маркер на соседнюю клавишу слева;\n" +
                    "    - \"вправо\" - передвигает маркер на соседнюю клавишу справа.\n" +
                    "Для взаимодействия с выбранной, с помощью команд управления маркером,\n" +
                    "клавишей, определены следующие команды:\n" +
                    "    - \"нажать\" - нажимает клавишу и удерживает её в таком положении;\n" +
                    "    - \"отпустить\" - отпускает ранее нажатую клавишу;\n" +
                    "    - \"ввод\" - нажимает и отпускает клавишу.\n" +
                    "В случае ошибочного ввода символа введена команда:\n" +
                    "    - \"стереть\" - стирает последний введенный символ.\n" +
                    "Также в этом режиме доступны следующие команды:\n" +
                    "    - \"мышь\" для активации режима управления мышью;\n" +
                    "    - \"команды\" для активации режима голосовых комманд;\n" +
                    "    - \"блокировать\" для активации режима блокировки системы;\n" +
                    "    - \"помощь\" для получения справки по режиму;\n" +
                    "    - \"завершить работу\" для завершения работы с программой.\n";
    private Robot robot;

    public VirtualKeyboard virtualKeyboard;

    public KeyboardMode(Robot robot) {
        this.robot = robot;
        virtualKeyboard = new VirtualKeyboard();
        addCommand("помощь", new HelpCommand(HELP_MESSAGE));
        addCommand("вверх", new KeyboardMoveCommand(virtualKeyboard, KeyboardMoveCommand.Action.UP));
        addCommand("вниз", new KeyboardMoveCommand(virtualKeyboard, KeyboardMoveCommand.Action.DOWN));
        addCommand("влево", new KeyboardMoveCommand(virtualKeyboard, KeyboardMoveCommand.Action.LEFT));
        addCommand("вправо", new KeyboardMoveCommand(virtualKeyboard, KeyboardMoveCommand.Action.RIGHT));
        addCommand("стереть", new KeyPressCommand(robot, KeyEvent.VK_BACK_SPACE, KeyPressCommand.Action.CLICK));
    }

    @Override
    public void activate() {
        virtualKeyboard.setVisible(true);
    }

    @Override
    public void deactivate() {
        virtualKeyboard.setVisible(false);
    }

    @Override
    public String getName() {
        return "Режим голосового управления клавиатурой";
    }

    @Override
    public String getHelpMessage() {
        return HELP_MESSAGE;
    }

    @Override
    public String getGrammarName() {
        return "keyboardMode";
    }

    @Override
    public Command parseCommand(String command) {
        Command c = super.parseCommand(command);
        if (c != null) return c;
        if (command.equals("нажать"))
            return new KeyPressCommand(robot, virtualKeyboard.getKeyCode(), KeyPressCommand.Action.PRESS);
        if (command.equals("отпустить"))
            return new KeyPressCommand(robot, virtualKeyboard.getKeyCode(), KeyPressCommand.Action.RELEASE);
        if (command.equals("ввод"))
            return new KeyPressCommand(robot, virtualKeyboard.getKeyCode(), KeyPressCommand.Action.CLICK);
        return null;
    }
}
