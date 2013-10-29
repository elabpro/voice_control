package ru.irgups.po_08_1.group1.vocalCommander.mode;

import ru.irgups.po_08_1.group1.vocalCommander.command.HelpCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.MouseClickCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.MouseMoveCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.MouseScrollCommand;
import ru.irgups.po_08_1.group1.vocalCommander.gui.Net;

import java.awt.Robot;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MouseMode extends SimpleMode {
    public static final String HELP_MESSAGE =
            "Вы находитесь в режиме управления мышью. В этом режиме экран разбивается на 9 пронумерованных областей\n" +
                    "Для выбора конкретной области используются команды:\n" +
                    "    - \"один\"   \"два\"    \"три\"\n" +
                    "    - \"четыре\" \"пять\"   \"шесть\"\n" +
                    "    - \"семь\"   \"восемь\" \"девять\"\n" +
                    "После выбора одной из областей, она становится активной, и следующее разбиение происходит в этой области.\n" +
                    "Для возврата к предудущей области используется команда:\n" +
                    "    - \"назад\"\n" +
                    "Для возврата на самый верхний уровень используется команда:\n" +
                    "    - \"мышь\"\n" +
                    "Для клика левой, правой или средней кнопкой мыши, соответственно команды:\n" +
                    "    - \"левая\" \"правая\" \"средняя\"\n" +
                    "Для нажатия:\n" +
                    "    - \"нажать левую\" \"нажать правую\" \"нажать среднюю\"\n" +
                    "Для того,чтобы отпустить кнопку:\n" +
                    "    - \"отпустить левую\" \"отпустить правую\" \"отпустить среднюю\"\n" +
                    "Для выполнения двойного клика:\n" +
                    "    - \"дважды левая\" \"дважды правая\" \"дважды средняя\"\n" +
                    "Для прокрутки колесиком мыши вверх или вниз:\n" +
                    "    - \"прокрутка вверх\" \"прокрутка вниз\"\n" +
                    "Также вам доступны команды:\n" +
                    "    - \"клавиатура\" для активации режима управления клавиатурой;\n" +
                    "    - \"блокировать\" для активации режима блокировки системы;\n" +
                    "    - \"помощь\" для получения справки по режиму;\n" +
                    "    - \"завершить работу\" для завершения работы с программой;";

    private List<MouseMoveCommand.Action> mouseHistory;
    private Net net;

    public MouseMode(Robot robot) {
        mouseHistory = Collections.synchronizedList(new LinkedList<MouseMoveCommand.Action>());
        net = new Net(robot, mouseHistory);

        /*
        DONE: Распознавать, создавать и возвращать объекты команд:
        помощь

        один
        два
        три
        четыре
        пять
        шесть
        семь
        восемь
        девять

        назад

        левая
        средняя
        правая

        нажать левую
        нажать среднюю
        нажать правую

        отпустить левую
        отпустить среднюю
        отпустить правую

        дважды левая
        дважды средняя
        дважды правая

        прокрутка вверх
        прокрутка вниз
        */

        addCommand("помощь", new HelpCommand(HELP_MESSAGE));

        addCommand("один", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.ONE, mouseHistory));
        addCommand("два", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.TWO, mouseHistory));
        addCommand("три", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.THREE, mouseHistory));
        addCommand("четыре", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.FOUR, mouseHistory));
        addCommand("пять", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.FIVE, mouseHistory));
        addCommand("шесть", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.SIX, mouseHistory));
        addCommand("семь", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.SEVEN, mouseHistory));
        addCommand("восемь", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.EIGHT, mouseHistory));
        addCommand("девять", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.NINE, mouseHistory));

        addCommand("назад", new MouseMoveCommand(robot, net, MouseMoveCommand.Action.BACK, mouseHistory));

        addCommand("левая", new MouseClickCommand(robot, net, MouseClickCommand.Button.LEFT, MouseClickCommand.Action.CLICK));
        addCommand("средняя", new MouseClickCommand(robot, net, MouseClickCommand.Button.MIDDLE, MouseClickCommand.Action.CLICK));
        addCommand("правая", new MouseClickCommand(robot, net, MouseClickCommand.Button.RIGHT, MouseClickCommand.Action.CLICK));

        addCommand("нажать левую", new MouseClickCommand(robot, net, MouseClickCommand.Button.LEFT, MouseClickCommand.Action.PRESS));
        addCommand("нажать среднюю", new MouseClickCommand(robot, net, MouseClickCommand.Button.MIDDLE, MouseClickCommand.Action.PRESS));
        addCommand("нажать правую", new MouseClickCommand(robot, net, MouseClickCommand.Button.RIGHT, MouseClickCommand.Action.PRESS));

        addCommand("отпустить левую", new MouseClickCommand(robot, net, MouseClickCommand.Button.LEFT, MouseClickCommand.Action.RELEASE));
        addCommand("отпустить среднюю", new MouseClickCommand(robot, net, MouseClickCommand.Button.MIDDLE, MouseClickCommand.Action.RELEASE));
        addCommand("отпустить правую", new MouseClickCommand(robot, net, MouseClickCommand.Button.RIGHT, MouseClickCommand.Action.RELEASE));

        addCommand("дважды левая", new MouseClickCommand(robot, net, MouseClickCommand.Button.LEFT, MouseClickCommand.Action.DOUBLE_CLICK));
        addCommand("дважды средняя", new MouseClickCommand(robot, net, MouseClickCommand.Button.MIDDLE, MouseClickCommand.Action.DOUBLE_CLICK));
        addCommand("дважды правая", new MouseClickCommand(robot, net, MouseClickCommand.Button.RIGHT, MouseClickCommand.Action.DOUBLE_CLICK));

        addCommand("прокрутка вверх", new MouseScrollCommand(robot, net, MouseScrollCommand.Action.UP));
        addCommand("прокрутка вниз", new MouseScrollCommand(robot, net, MouseScrollCommand.Action.DOWN));
    }

    @Override
    public void activate() {
        // DONE: Сделать сетку видимой (setVisible())
        mouseHistory.clear();

        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        net.refresh();
        net.setVisible(true);
    }

    @Override
    public void deactivate() {
        // DONE: Сделать сетку невидимой (setVisible())
        net.setVisible(false);
        // Ожидание проигрывания системных анимаций.
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String getName() {
        return "Режим голосового управления мышью";
    }

    @Override
    public String getHelpMessage() {
        return HELP_MESSAGE;
    }

    @Override
    public String getGrammarName() {
        return "mouseMode";
    }
}
