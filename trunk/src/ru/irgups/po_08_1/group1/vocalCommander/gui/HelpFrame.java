package ru.irgups.po_08_1.group1.vocalCommander.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.HeadlessException;

public class HelpFrame extends JFrame {
    public HelpFrame(String message) throws HeadlessException {
        // DONE: окно помощи готово
        super("Окно помощи");

        ImageIcon icon = new ImageIcon("./image/help.png");
        setIconImage(icon.getImage()); //создаем новый объект с иконкой на окне

        JLabel mess = new JLabel(convertStringInHtml(message)); //создаем объект с html-строкой
        mess.setVerticalAlignment(SwingConstants.TOP);
        mess.setHorizontalAlignment(SwingConstants.CENTER); //текст наверху, в центре
        add(mess); //добавляем наше сообщение в JLabel
        pack(); //задаем оптимальный размер окна

        // DONE: Испытать в HelpFrame для центрирования окна
        setLocationRelativeTo(null); //центрирование окна
        setResizable(false); // запретить изменение размеров окна
    }

    //метод для перевода сообщения в html-текст
    private String convertStringInHtml(String message) {
        message = message.replace("\r\n", "<br>");
        message = message.replace("\n", "<br>");
        message = message.replace("\r", "<br>"); //замена тэгов
        StringBuilder builder = new StringBuilder(message); // создаем новый объект
        builder.insert(0, "<html>"); //ставим на 1 место строки тег
        builder.append("</html>"); //ставим в конец строки тег
        return builder.toString();
    }
}
