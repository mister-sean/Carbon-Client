package me.toby.carbon.features.command.commands;

import me.toby.carbon.OyVey;
import me.toby.carbon.features.command.Command;

public class ReloadCommand
        extends Command {
    public ReloadCommand() {
        super("reload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        OyVey.reload();
    }
}
