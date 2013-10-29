package ru.irgups.po_08_1.group1.vocalCommander.command;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.IOException;

@XStreamAlias("ExternalCommand")
public class ExternalCommand implements Command {
    @XStreamAlias("path")
    private String command;

    public ExternalCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
