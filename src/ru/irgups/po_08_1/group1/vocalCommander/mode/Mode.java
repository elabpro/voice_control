package ru.irgups.po_08_1.group1.vocalCommander.mode;

import ru.irgups.po_08_1.group1.vocalCommander.command.Command;

public interface Mode {
    // DONE: продумать использование реализации Mode не как синглтона

    public void addCommand(String cause, Command result);

    // DONE: изменить сигнатуру на parseCommand(String command), все остальные параметры передавать через конструктор
    public Command parseCommand(String command);

    public void activate();

    public void deactivate();

    public String getName();

    public String getHelpMessage();

    // DONE: решить вопрос с изменением словаря распознавателя
    public String getGrammarName();
}
