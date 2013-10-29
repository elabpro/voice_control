package ru.irgups.po_08_1.group1.vocalCommander.command;

import ru.irgups.po_08_1.group1.vocalCommander.VocalCommander;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        VocalCommander.stop();
    }
}
