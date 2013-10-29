package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.gui.VirtualKeyboard;

public class KeyboardMoveCommand implements Command {
    public static enum Action {
        UP, DOWN, LEFT, RIGHT
    }

    private VirtualKeyboard virtualKeyboard;
    private Action action;

    public KeyboardMoveCommand(VirtualKeyboard virtualKeyboard, Action action) {
        this.virtualKeyboard = virtualKeyboard;
        this.action = action;
    }

    @Override
    public void execute() {
        if (action == Action.UP) {
            virtualKeyboard.moveUp();
        }

        if (action == Action.DOWN) {
            virtualKeyboard.moveDown();
        }

        if (action == Action.LEFT) {
            virtualKeyboard.moveLeft();
        }

        if (action == Action.RIGHT) {
            virtualKeyboard.moveRight();
        }
    }
}