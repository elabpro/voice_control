package ru.irgups.po_08_1.group1.vocalCommander.mode;

import edu.cmu.sphinx.jsgf.JSGFGrammar;
import ru.irgups.po_08_1.group1.vocalCommander.ModeSwitcher;
import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.HelpCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.SwitchModeCommand;

public class LockMode extends SimpleMode {
    public static final String HELP_MESSAGE =
            "Вы находитесь в режиме временной блокировки программы. В данном режиме вам доступны следующие команды:\n" +
                    "    - \"разблокировать\" для разблокировки системы и возврата в предыдущий режим;\n" +
                    "    - \"помощь\" для получения справки по режиму;";

    /*
    DONE: Распознавать, создавать и возвращать объекты команд:
    помощь
    */

    private ModeSwitcher switcher;
    private JSGFGrammar grammar;

    public LockMode(ModeSwitcher switcher, JSGFGrammar grammar) {
        this.switcher = switcher;
        this.grammar = grammar;

        addCommand("помощь", new HelpCommand(HELP_MESSAGE));
    }

    @Override
    public Command parseCommand(String command) {
        Command c = super.parseCommand(command);
        if (c != null) return c;
        if (command.equals("разблокировать"))
            return new SwitchModeCommand(switcher, grammar, switcher.getPreviousMode());
        return null;
    }

    @Override
    public String getName() {
        return "Режим временной блокировки программы";
    }

    @Override
    public String getHelpMessage() {
        return HELP_MESSAGE;
    }

    @Override
    public String getGrammarName() {
        return "lockMode";
    }
}
