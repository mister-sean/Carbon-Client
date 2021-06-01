package me.toby.carbon.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : Carbon.commandManager.getCommands()) {
            HelpCommand.sendMessage(ChatFormatting.GRAY + Carbon.commandManager.getPrefix() + command.getName());
        }
    }
}

