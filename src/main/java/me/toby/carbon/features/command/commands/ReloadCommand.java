package me.toby.carbon.features.command.commands;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;

public class ReloadCommand extends Command
{
    public ReloadCommand() {
        super("reload", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        McDonalds.reload();
    }
}
