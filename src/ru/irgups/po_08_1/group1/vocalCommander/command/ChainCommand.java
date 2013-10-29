package ru.irgups.po_08_1.group1.vocalCommander.command;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.LinkedList;
import java.util.List;

@XStreamAlias("ChainCommand")
public class ChainCommand implements Command {
    @XStreamImplicit(itemFieldName = "effect")
    private List<Command> commands;

    public ChainCommand() {
        commands = new LinkedList<Command>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public void execute() {
        for (Command command : commands) command.execute();
    }
}
