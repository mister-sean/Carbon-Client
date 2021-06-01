package me.toby.carbon.features.modules.player;

import me.toby.carbon.features.modules.Module;

public class MultiTask extends Module {
    private static MultiTask INSTANCE = new MultiTask();

    public MultiTask() {
        super("MultiTask", "Allows you to eat while mining.", Module.Category.PLAYER, false, false, false);
        setInstance();
    }

    public static MultiTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MultiTask();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

