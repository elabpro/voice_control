package ru.irgups.po_08_1.group1.vocalCommander.command;

import edu.cmu.sphinx.jsgf.JSGFGrammar;
import ru.irgups.po_08_1.group1.vocalCommander.ModeSwitcher;
import ru.irgups.po_08_1.group1.vocalCommander.VocalCommander;
import ru.irgups.po_08_1.group1.vocalCommander.mode.Mode;

public class SwitchModeCommand implements Command {
    private ModeSwitcher switcher;
    private JSGFGrammar grammar;
    private Mode mode;

    public SwitchModeCommand(ModeSwitcher switcher, JSGFGrammar grammar, Mode mode) {
        this.switcher = switcher;
        this.grammar = grammar;
        this.mode = mode;
    }

    @Override
    public void execute() {
        // Отладочный вывод
        System.out.print("Переход в ");
        System.out.println(mode.getName());

        switcher.getActiveMode().deactivate();
        switcher.setActiveMode(mode);
        switcher.getActiveMode().activate();

        // DONE: решить вопрос с изменением словаря распознавателя
        synchronized (VocalCommander.class) {
            try {
                grammar.loadJSGF(mode.getGrammarName());

                switcher.setUpdated(true);
                VocalCommander.class.notifyAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
