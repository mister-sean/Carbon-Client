package me.toby.carbon.features.command.commands;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;

public class UnloadCommand extends Command
{
    public UnloadCommand() {
        super("unload", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        Carbon.unload(true);
    }
}
