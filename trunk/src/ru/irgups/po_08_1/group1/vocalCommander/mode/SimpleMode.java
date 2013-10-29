package ru.irgups.po_08_1.group1.vocalCommander.mode;

import ru.irgups.po_08_1.group1.vocalCommander.command.Command;

import java.util.HashMap;
import java.util.Map;

public class SimpleMode implements Mode {
    private Map<String, Command> map = new HashMap<String, Command>();

    @Override
    public void addCommand(String cause, Command result) {
        map.put(cause, result);
    }

    @Override
    public Command parseCommand(String command) {
        return map.get(command);
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getHelpMessage(){
        return null;
    }

    @Override
    public String getGrammarName() {
        return null;
    }
}
