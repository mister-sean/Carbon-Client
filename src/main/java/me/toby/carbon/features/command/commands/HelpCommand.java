package me.toby.carbon.features.command.commands;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void execute(final String[] commands) {
        Command.sendMessage("Commands: ");
        for (final Command command : McDonalds.commandManager.getCommands()) {
            Command.sendMessage(ChatFormatting.GRAY + McDonalds.commandManager.getPrefix() + command.getName());
        }
    }
}
