package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.gui.Net;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseClickCommand implements Command {
    public static enum Button {
        LEFT, MIDDLE, RIGHT
    }

    public static enum Action {
        PRESS, RELEASE, CLICK, DOUBLE_CLICK
    }

    private Robot robot;
    private Net net;
    private Button button;
    private Action action;

    public MouseClickCommand(Robot robot, Net net, Button button, Action action) {
        this.robot = robot;
        this.net = net;
        this.button = button;
        this.action = action;
    }

    @Override
    public void execute() {
        // DONE: Вызвать нужное действие у робота
        // Используемая кнопка мыши хранится в поле класса button
        // Нужное действие хранится в поле класса action
        // Для ссылки на робота использовать robot

        net.setVisible(false);
        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        //Нажимаем
        if (action == Action.PRESS & button == Button.LEFT) robot.mousePress(InputEvent.BUTTON1_MASK);
        if (action == Action.PRESS & button == Button.MIDDLE) robot.mousePress(InputEvent.BUTTON2_MASK);
        if (action == Action.PRESS & button == Button.RIGHT) robot.mousePress(InputEvent.BUTTON3_MASK);

        //Отпускаем
        if (action == Action.RELEASE & button == Button.LEFT) robot.mouseRelease(InputEvent.BUTTON1_MASK);
        if (action == Action.RELEASE & button == Button.MIDDLE) robot.mouseRelease(InputEvent.BUTTON2_MASK);
        if (action == Action.RELEASE & button == Button.RIGHT) robot.mouseRelease(InputEvent.BUTTON3_MASK);

        //Кликаем
        if (action == Action.CLICK & button == Button.LEFT) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
        if (action == Action.CLICK & button == Button.MIDDLE) {
            robot.mousePress(InputEvent.BUTTON2_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_MASK);
        }
        if (action == Action.CLICK & button == Button.RIGHT) {
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        }

        //Двойной клик
        if (action == Action.DOUBLE_CLICK & button == Button.LEFT) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
        if (action == Action.DOUBLE_CLICK & button == Button.MIDDLE) {
            robot.mousePress(InputEvent.BUTTON2_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_MASK);
            robot.mousePress(InputEvent.BUTTON2_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_MASK);
        }
        if (action == Action.DOUBLE_CLICK & button == Button.RIGHT) {
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        }

        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        net.refresh();
        net.setVisible(true);
    }
}