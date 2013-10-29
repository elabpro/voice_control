package ru.irgups.po_08_1.group1.vocalCommander;

import ru.irgups.po_08_1.group1.vocalCommander.mode.Mode;

public class ModeSwitcher {
    private Mode previousMode;
    private Mode activeMode;
    private boolean isUpdated;

    public synchronized Mode getPreviousMode() {
        return previousMode;
    }

    public synchronized Mode getActiveMode() {
        return activeMode;
    }

    public synchronized void setActiveMode(Mode activeMode) {
        previousMode = this.activeMode;
        this.activeMode = activeMode;
    }

    public synchronized boolean isUpdated() {
        return isUpdated;
    }

    public synchronized void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
