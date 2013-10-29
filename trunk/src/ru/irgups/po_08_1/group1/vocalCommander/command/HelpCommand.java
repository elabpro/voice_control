package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.gui.HelpFrame;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class HelpCommand implements Command {
    private String message;

    public HelpCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // DONE: Создать окно помощи (HelpFrame) и сделать его видимым (setVisible())
                // DONE:  HelpFrame  реализован

                // Сообщение для окна помощи хранится в поле класса message
                JFrame helpFrame = new HelpFrame(message);
                helpFrame.setVisible(true);

                // Отладочный вывод
                System.out.println(message);
            }
        });
    }
}
