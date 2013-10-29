package ru.irgups.po_08_1.group1.vocalCommander.gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class VirtualKeyboard extends JDialog {
    private int[][] codes;
    private DefaultTableModel modelKeys;
    private JTable table;
    private int currentRow;
    private int currentColumn;

    public VirtualKeyboard() {
        // Коды клавиш (для Robot)
        codes = new int[][]{
                {KeyEvent.VK_ESCAPE,        KeyEvent.VK_F1,         KeyEvent.VK_F2,     KeyEvent.VK_F3,         KeyEvent.VK_F4,         KeyEvent.VK_F5,         KeyEvent.VK_F6,     KeyEvent.VK_F7,         KeyEvent.VK_F8,         KeyEvent.VK_F9,         KeyEvent.VK_F10,        KeyEvent.VK_F11,            KeyEvent.VK_F12,            KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,      KeyEvent.VK_UNDEFINED,      KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED   },
                {KeyEvent.VK_BACK_QUOTE,    KeyEvent.VK_1,          KeyEvent.VK_2,      KeyEvent.VK_3,          KeyEvent.VK_4,          KeyEvent.VK_5,          KeyEvent.VK_6,      KeyEvent.VK_7,          KeyEvent.VK_8,          KeyEvent.VK_9,          KeyEvent.VK_0,          KeyEvent.VK_MINUS,          KeyEvent.VK_EQUALS,         KeyEvent.VK_BACK_SPACE, KeyEvent.VK_PRINTSCREEN,    KeyEvent.VK_SCROLL_LOCK,    KeyEvent.VK_PAUSE,      KeyEvent.VK_NUM_LOCK,   KeyEvent.VK_DIVIDE,     KeyEvent.VK_MULTIPLY,   KeyEvent.VK_MINUS  },
                {KeyEvent.VK_TAB,           KeyEvent.VK_Q,          KeyEvent.VK_W,      KeyEvent.VK_E,          KeyEvent.VK_R,          KeyEvent.VK_T,          KeyEvent.VK_Y,      KeyEvent.VK_U,          KeyEvent.VK_I,          KeyEvent.VK_O,          KeyEvent.VK_P,          KeyEvent.VK_OPEN_BRACKET,   KeyEvent.VK_CLOSE_BRACKET,  KeyEvent.VK_BACK_SLASH, KeyEvent.VK_INSERT,         KeyEvent.VK_HOME,           KeyEvent.VK_PAGE_UP,    KeyEvent.VK_NUMPAD7,    KeyEvent.VK_NUMPAD8,    KeyEvent.VK_NUMPAD9,    KeyEvent.VK_PLUS       },
                {KeyEvent.VK_CAPS_LOCK,     KeyEvent.VK_A,          KeyEvent.VK_S,      KeyEvent.VK_D,          KeyEvent.VK_F,          KeyEvent.VK_G,          KeyEvent.VK_H,      KeyEvent.VK_J,          KeyEvent.VK_K,          KeyEvent.VK_L,          KeyEvent.VK_SEMICOLON,  KeyEvent.VK_QUOTE,          KeyEvent.VK_UNDEFINED,      KeyEvent.VK_ENTER,      KeyEvent.VK_DELETE,         KeyEvent.VK_END,            KeyEvent.VK_PAGE_DOWN,  KeyEvent.VK_NUMPAD4,    KeyEvent.VK_NUMPAD5,    KeyEvent.VK_NUMPAD6,    KeyEvent.VK_UNDEFINED        },
                {KeyEvent.VK_SHIFT,         KeyEvent.VK_Z,          KeyEvent.VK_X,      KeyEvent.VK_C,          KeyEvent.VK_V,          KeyEvent.VK_B,          KeyEvent.VK_N,      KeyEvent.VK_M,          KeyEvent.VK_COMMA,      KeyEvent.VK_PERIOD,     KeyEvent.VK_DIVIDE,     KeyEvent.VK_UNDEFINED,      KeyEvent.VK_UNDEFINED,      KeyEvent.VK_SHIFT,      KeyEvent.VK_UNDEFINED,      KeyEvent.VK_UP,             KeyEvent.VK_UNDEFINED,  KeyEvent.VK_NUMPAD1,    KeyEvent.VK_NUMPAD2,    KeyEvent.VK_NUMPAD3,    KeyEvent.VK_UNDEFINED   },
                {KeyEvent.VK_CONTROL,       KeyEvent.VK_WINDOWS,    KeyEvent.VK_ALT,    KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_SPACE,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_UNDEFINED,  KeyEvent.VK_ALT,        KeyEvent.VK_WINDOWS,        KeyEvent.VK_META,           KeyEvent.VK_CONTROL,    KeyEvent.VK_LEFT,           KeyEvent.VK_DOWN,           KeyEvent.VK_RIGHT,      KeyEvent.VK_NUMPAD0,    KeyEvent.VK_UNDEFINED,  KeyEvent.VK_PERIOD,     KeyEvent.VK_ENTER       },
        };

        // Создаем модель раскладки виртуальной клавиатуры
        modelKeys = new DefaultTableModel();
        String[] columnName = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        Object[][] keyMap = new Object[][]{
                {"Esc", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "", "", "", "", "", "", "", ""},
                {"Ёё ~`", "1 !", "2 \" @", "3 № #", "4 ; $", "5 %", "6 : ^", "7 ? &", "8 *", "9 (", "0 )", "- _", "= +", "Bksp", "PrtScr", "Scroll", "Pause", "Num", "/", "*", "-"},
                {"Tab", "Йй Qq", "Цц Ww", "Уу Ee", "Кк Rr", "Ее Tt", "Нн Yy", "Гг Uu", "Шш Ii", "Щщ Oo", "Зз Pp", "Хх {[", "Ъъ }]", "\\ / | \\", "Insert", "Home", "PgUp", "7", "8", "9", "+"},
                {"Caps", "Фф Aa", "Ыы Ss", "Вв Dd", "Аа Ff", "Пп Gg", "Рр Hh", "Оо Jj", "Лл Kk", "Дд Ll", "Жж :;", "Ээ \" '", "", "Enter", "Delete", "End", "PgDn", "4", "5", "6", ""},
                {"Shift", "Яя Zz", "Чч Xx", "Сс Cc", "Мм Vv", "Ии Bb", "Тт Nn", "Ьь Mm", "Бб <,", "Юю >.", "., ?/", "", "", "Shift", "", "↑", "", "1", "2", "3", ""},
                {"Ctrl", "Win", "Alt", "", "", "", "Space", "", "", "", "Alt", "Win", "Menu", "Ctrl", "←", "↓", "→", "0", "", ".", "Enter"},
        };

        // Конфигурируем модели
        modelKeys.setDataVector(keyMap, columnName);

        // Создаем и конфигурируем таблицу на основе модели
        table = new JTable(modelKeys);

        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(20);

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
        renderer.setHorizontalAlignment(JLabel.CENTER);

        // Устанавливаем позицию текущей клавиши
        currentRow = 3;
        currentColumn = 13;

        // Устанавливаем размер окна виртуальной клавиатуры
        Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setLocation(dim.width / 11, dim.height - dim.height / 6);
        dim.width /= 1.2;
        dim.height = table.getRowHeight() * 6;
        setSize(dim);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);

        // Размещаем компоненты
        add(table);

        // Обновляем клавиатуру
        refreshKeyboard();
    }

    // Обновление клавиатуры
    private void refreshKeyboard() {
        table.changeSelection(currentRow, currentColumn, false, false);
    }

    public void moveUp() {
        if (currentRow > 0) {
            currentRow--;
        } else {
            currentRow = table.getRowCount() - 1;
        }
        refreshKeyboard();
    }

    public void moveDown() {
        if (currentRow < table.getRowCount() - 1) {
            currentRow++;
        } else {
            currentRow = 0;
        }
        refreshKeyboard();
    }

    public void moveLeft() {
        if (currentColumn > 0) {
            currentColumn--;
        } else {
            currentColumn = table.getColumnCount() - 1;
        }
        refreshKeyboard();
    }

    public void moveRight() {
        if (currentColumn < table.getColumnCount() - 1) {
            currentColumn++;
        } else {
            currentColumn = 0;
        }
        refreshKeyboard();
    }

    public int getKeyCode() {
        return codes[currentRow][currentColumn];
    }
}
