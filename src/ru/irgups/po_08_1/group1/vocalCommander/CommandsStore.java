package ru.irgups.po_08_1.group1.vocalCommander;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import ru.irgups.po_08_1.group1.vocalCommander.command.ChainCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.ExternalCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.KeyPressCommand;

import java.awt.Robot;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class CommandsStore {
    @XStreamAlias("command")
    public static class Wrapper {
        public String cause;
        public Command effect;

        public Wrapper(String cause, Command effect) {
            this.cause = cause;
            this.effect = effect;
        }
    }

    private File commandsFile;
    private File grammarFile;
    private XStream stream;

    public CommandsStore(File commandsFile, File grammarFile) {
        this.commandsFile = commandsFile;
        this.grammarFile = grammarFile;
        stream = new XStream();
        stream.processAnnotations(Wrapper.class);
        stream.processAnnotations(ChainCommand.class);
        stream.processAnnotations(KeyPressCommand.class);
        stream.processAnnotations(ExternalCommand.class);
    }

    public void store(List<Wrapper> wrappers) throws IOException {
        stream.alias("commands", wrappers.getClass());

        Writer writer = new FileWriter(commandsFile);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        if (wrappers.isEmpty()) writer.write("<commands>\r\n</commands>");
        else stream.toXML(wrappers, writer);
        writer.close();

        recoveryGrammar(wrappers);
    }

    public List<Wrapper> load(Robot robot) throws IOException {
        stream.alias("commands", LinkedList.class);

        Reader reader = new FileReader(commandsFile);
        List<Wrapper> wrappers = (LinkedList<Wrapper>) stream.fromXML(reader);
        reader.close();

        for (Wrapper wrapper : wrappers) recoveryCommand(wrapper.effect, robot);
        recoveryGrammar(wrappers);

        return wrappers;
    }

    private void recoveryCommand(Command command, Robot robot) {
        if (command instanceof ChainCommand)
            for (Command subCommand : ((ChainCommand) command).getCommands())
                recoveryCommand(subCommand, robot);

        if (command instanceof KeyPressCommand)
            ((KeyPressCommand) command).setRobot(robot);
    }

    private void recoveryGrammar(List<Wrapper> wrappers) throws IOException {
        StringBuilder grammar = new StringBuilder();
        String grammarName = grammarFile.getName().split("\\.")[0];
        grammar.append(String.format("#JSGF V1.0;\r\n\r\ngrammar %s;\r\n\r\n<commands> = ", grammarName));
        String delimiter = "";
        for (Wrapper wrapper : wrappers) {
            grammar.append(delimiter);
            grammar.append(wrapper.cause);
            delimiter = " | ";
        }
        if (wrappers.isEmpty()) grammar.append("<VOID>");
        grammar.append(";");

        Writer writer = new FileWriter(grammarFile);
        writer.write(grammar.toString());
        writer.close();
    }
}
