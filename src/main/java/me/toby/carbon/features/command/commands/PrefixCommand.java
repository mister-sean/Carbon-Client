package me.toby.carbon.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", new String[] { "<char>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(ChatFormatting.GREEN + "Current prefix is " + Carbon.commandManager.getPrefix());
            return;
        }
        Carbon.commandManager.setPrefix(commands[0]);
        Command.sendMessage("Prefix changed to " + ChatFormatting.GRAY + commands[0]);
    }
}
