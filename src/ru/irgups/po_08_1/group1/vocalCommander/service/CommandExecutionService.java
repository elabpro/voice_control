package ru.irgups.po_08_1.group1.vocalCommander.service;

import ru.irgups.po_08_1.group1.vocalCommander.command.Command;

import java.util.concurrent.BlockingQueue;

public class CommandExecutionService implements Runnable {
    private final BlockingQueue<Command> commandQueue;

    public CommandExecutionService(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        // Отладочный вывод
        System.out.println("Сервис выполнения команд запущен.");

        while (!Thread.interrupted()) {
            try {
                commandQueue.take().execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Отладочный вывод
        System.out.println("Сервис выполнения команд остановлен.");

        System.exit(0);
    }
}
