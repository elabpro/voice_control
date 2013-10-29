package ru.irgups.po_08_1.group1.vocalCommander.command;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.awt.Robot;
import java.awt.event.KeyEvent;

@XStreamAlias("KeyPressCommand")
public class KeyPressCommand implements Command {
    public static enum Action {
        PRESS, RELEASE, CLICK
    }

    @XStreamOmitField
    private Robot robot;
    private int code;
    private Action action;

    public KeyPressCommand(Robot robot, int code, Action action) {
        this.robot = robot;
        this.code = code;
        this.action = action;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void execute() {
        if (code != KeyEvent.VK_UNDEFINED) {
            if (action == Action.PRESS) {
                robot.keyPress(code);
            }

            if (action == Action.RELEASE) {
                robot.keyRelease(code);
            }

            if (action == Action.CLICK) {
                robot.keyPress(code);
                robot.keyRelease(code);
            }
        }
    }
}
