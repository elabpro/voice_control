package ru.irgups.po_08_1.group1.vocalCommander.gui;

import ru.irgups.po_08_1.group1.vocalCommander.command.MouseMoveCommand;

import javax.swing.JWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.List;

public class Net extends JWindow {
    private Robot robot;
    private List<MouseMoveCommand.Action> mouseHistory;

    private GraphicsDevice device;
    private Image image;

    public Net(Robot robot, final List<MouseMoveCommand.Action> mouseHistory) throws HeadlessException {
        // DONE: инициализировать окно
        // которое занимает весь экран (setExtendedState())
        // которое находится "всегда наверху" (setAlwaysOnTop())
        // которое не имеет рамки (setUndecorated())
        // которое полупрозрачно
        // на котором нарисована сетка
        // NOTE: Изменена реализация, окно необходимо скрывать для клика
        // которое пропускает сквозь себя клики

        this.robot = robot;
        this.mouseHistory = mouseHistory;

        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        enableInputMethods(false);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
    }

    public void refresh() {
        image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    private void drawNet(Graphics2D g2, Rectangle area) {
        int stepX = area.width / 3, stepY = area.height / 3;
        for (int i = 1; i < 3; ++i)
            g2.drawLine(area.x + i * stepX, area.y, area.x + i * stepX, area.y + area.height);
        for (int i = 1; i < 3; ++i)
            g2.drawLine(area.x, area.y + i * stepY, area.x + area.width, area.y + i * stepY);
    }

    private void drawNumbers(Graphics2D g2, Rectangle area) {
        int stepX = area.width / 3, stepY = area.height / 3;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                g2.drawString(String.valueOf(i * 3 + j + 1), area.x + j * stepX + 5, area.y + i * stepY + 20);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();

        g2D.drawImage(image, 0, 0, null);

        Rectangle area = getBounds();
        int dirX = 0, dirY = 0;

        g2D.setColor(Color.RED);
        drawNet(g2D, area);
        for (MouseMoveCommand.Action action : mouseHistory) {
            area.x += area.width / 3;
            area.y += area.height / 3;
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
            area.x += dirX * area.width;
            area.y += dirY * area.height;

            drawNet(g2D, area);
        }

        if (area.width >= 140) {
            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 16f));
            drawNumbers(g2D, area);
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            super.setVisible(true);
            device.setFullScreenWindow(this);
            repaint();
        } else {
            device.setFullScreenWindow(null);
            super.setVisible(false);
        }
    }
}
