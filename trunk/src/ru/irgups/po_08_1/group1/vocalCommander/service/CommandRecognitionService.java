package ru.irgups.po_08_1.group1.vocalCommander.service;

import edu.cmu.sphinx.frontend.DataProcessingException;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import ru.irgups.po_08_1.group1.vocalCommander.ModeSwitcher;
import ru.irgups.po_08_1.group1.vocalCommander.VocalCommander;
import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.SwitchModeCommand;

import java.util.concurrent.BlockingQueue;

public class CommandRecognitionService implements Runnable {
    private Recognizer recognizer;
    private BlockingQueue<Command> commandQueue;
    private ModeSwitcher switcher;

    public CommandRecognitionService(Recognizer recognizer, BlockingQueue<Command> commandQueue, ModeSwitcher switcher) {
        this.recognizer = recognizer;
        this.commandQueue = commandQueue;
        this.switcher = switcher;
    }

    @Override
    public void run() {
        // Отладочный вывод
        System.out.println("Сервис распознавания речи запущен.");

        while (!Thread.interrupted()) {
            try {
                synchronized (VocalCommander.class) {
                    Result result = recognizer.recognize();
                    String text;
                    if (result != null) text = result.getBestFinalResultNoFiller();
                    else text = "";

                    // Отладочный вывод
                    System.out.print(String.format("Распознанная команда: \"%s\"\n", text));

                    Command command = switcher.getActiveMode().parseCommand(text);

                    if (command != null) commandQueue.add(command);

                    if (command instanceof SwitchModeCommand) {
                        switcher.setUpdated(false);
                        try {
                            while (!switcher.isUpdated()) VocalCommander.class.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            } catch (DataProcessingException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Отладочный вывод
        System.out.println("Сервис распознавания речи остановлен.");
    }
}
