package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.gui.Net;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.List;

public class MouseMoveCommand implements Command {
    public static enum Action {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, BACK
    }

    private Robot robot;
    private Net net;
    private Action action;
    private List<Action> mouseHistory;

    public MouseMoveCommand(Robot robot, Net net, Action action, List<Action> mouseHistory) {
        this.robot = robot;
        this.net = net;
        this.action = action;
        this.mouseHistory = mouseHistory;
    }

    @Override
    public void execute() {
        // DONE:
        // вычислить новые координаты мыши основываясь на текущей команде и истории мыши
        // дать роботу команду переместить мышь по нужным координатам
        // сообщить сетке о необходимости перерисоваться

        // Текущая команда хранится в поле класса action
        // Для ссылки на робота (объект, который управляет мышью) - использовать robot
        // Для ссылки на сетку - использовать net
        // Для ссылки на историю движений мыши - использовать mouseHistory

        net.setVisible(false);
        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        Dimension area = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 0, y = 0, dirX = 0, dirY = 0;

        if (action == Action.BACK) {
            if (!mouseHistory.isEmpty()) mouseHistory.remove(mouseHistory.size() - 1);
        } else mouseHistory.add(action);

        for (Action action : mouseHistory) {
            x += area.width / 3;
            y += area.height / 3;
            area.width /= 3;
            area.height /= 3;
            switch (action) {
                case ONE: case FOUR: case SEVEN: dirX = -1; break;
                case TWO: case FIVE: case EIGHT: dirX = 0; break;
                case THREE: case SIX: case NINE: dirX = 1; break;
            }
            switch (action) {
                case ONE: case TWO: case THREE: dirY = -1; break;
                case FOUR: case FIVE: case SIX: dirY = 0; break;
                case SEVEN: case EIGHT: case NINE: dirY = 1; break;
            }
            x += dirX * area.width;
            y += dirY * area.height;
        }

        x += area.width / 2;
        y += area.height / 2;
        robot.mouseMove(x, y);

        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        net.refresh();
        net.setVisible(true);
    }
}
