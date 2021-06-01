package me.toby.carbon.features.command.commands;

import me.toby.carbon.OyVey;
import me.toby.carbon.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        OyVey.unload(true);
    }
}

