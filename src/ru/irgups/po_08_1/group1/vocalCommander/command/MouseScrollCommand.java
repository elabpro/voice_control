package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.gui.Net;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseScrollCommand implements Command {
    private static final int SCROLL_AMOUNT = 3;

    public static enum Action {
        UP, DOWN
    }

    private Robot robot;
    private Net net;
    private Action action;

    public MouseScrollCommand(Robot robot, Net net, Action action) {
        this.robot = robot;
        this.net = net;
        this.action = action;
    }

    @Override
    public void execute() {
        // DONE: Вызвать нужное действие у робота
        // Нужное действие хранится в поле класса action
        // Для ссылки на робота использовать robot

        net.setVisible(false);
        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        if (action == Action.UP) robot.mouseWheel(-SCROLL_AMOUNT);
        if (action == Action.DOWN) robot.mouseWheel(SCROLL_AMOUNT);

        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        net.refresh();
        net.setVisible(true);
    }
}
