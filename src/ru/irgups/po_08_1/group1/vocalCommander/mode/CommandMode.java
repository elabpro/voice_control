package ru.irgups.po_08_1.group1.vocalCommander.mode;

import ru.irgups.po_08_1.group1.vocalCommander.command.HelpCommand;

public class CommandMode extends SimpleMode {
    public static final String HELP_MESSAGE =
            "Вы находитесь в режиме голосовых команд. В этом режиме вам доступны следующие команды:\n" +
                    "    - \"мышь\" для активации режима управления мышью;\n" +
                    "    - \"клавиатура\" для активации режима управления клавиатурой;\n" +
                    "    - \"блокировать\" для активации режима блокировки системы;\n" +
                    "    - \"помощь\" для получения справки по режиму;\n" +
                    "    - \"завершить работу\" для завершения работы с программой;\n";

    /*
    DONE: Распознавать, создавать и возвращать объекты команд:
    помощь
    */

    public CommandMode() {
        addCommand("помощь", new HelpCommand(HELP_MESSAGE));
    }

    @Override
    public String getName() {
        return "Режим голосовых команд";
    }

    @Override
    public String getHelpMessage() {
        return HELP_MESSAGE;
    }

    @Override
    public String getGrammarName() {
        return "commandMode";
    }
}
