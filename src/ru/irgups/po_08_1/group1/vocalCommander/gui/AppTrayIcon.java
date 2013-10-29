package ru.irgups.po_08_1.group1.vocalCommander.gui;

import ru.irgups.po_08_1.group1.vocalCommander.ModeSwitcher;
import ru.irgups.po_08_1.group1.vocalCommander.command.Command;
import ru.irgups.po_08_1.group1.vocalCommander.command.ExitCommand;
import ru.irgups.po_08_1.group1.vocalCommander.command.HelpCommand;
import ru.irgups.po_08_1.group1.vocalCommander.mode.Mode;

import javax.swing.ImageIcon;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

public class AppTrayIcon extends TrayIcon {
    public AppTrayIcon(final Queue<Command> commandQueue, final ModeSwitcher switcher) {
        // DONE:
        // инициализировать значок приложения в области уведомлений с иконкой и всплывающим меню
        // пункты меню:
        // - Помощь
        // - Выход
        // Сделать так, чтобы были видны лишь возможные в данный момент пункты меню
        // Назначить пунктам меню действия - создание соответствующих команд и добавление их в очередь команд

        // Пример работы с иконкой
        // http://stackoverflow.com/questions/331407/java-trayicon-using-image-with-transparent-background

        // Для ссылки на очередь команд использовать commandQueue
        // Для ссылки на переключатель режимов использовать switcher
        // Для ссылки на распознаватель использовать recognizer
        // http://www.rgagnon.com/javadetails/java-0612.html
        // http://stackoverflow.com/questions/5818028/how-to-make-java-trayicon-menuitem-shorcut
        // http://www.javaswing.org/java-swing-popup-menu.aspx
        // http://www.javaportal.ru/java/articles/jdesktop.html

        super(new ImageIcon("./image/microphone.png").getImage()); //создаем иконку
        setImageAutoSize(true); //задаем размер

        final PopupMenu menu = new PopupMenu("Меню");
        MenuItem menuHelp = new MenuItem("Помощь");
        menuHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mode mode = switcher.getActiveMode();
                Command command = new HelpCommand(mode.getHelpMessage());
                commandQueue.add(command);

            }
        });

        final MenuItem menuQuit = new MenuItem("Выход");
        menuQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandQueue.add(new ExitCommand());
            }
        });

        menu.add(menuHelp);
        menu.add(menuQuit);

        setPopupMenu(menu);
    }
}

