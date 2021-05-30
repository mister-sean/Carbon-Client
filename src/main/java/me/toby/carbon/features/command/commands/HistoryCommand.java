package me.toby.carbon.features.command.commands;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.features.command.Command;
import me.toby.carbon.util.PlayerUtil;

public class HistoryCommand extends Command
{
    public HistoryCommand() {
        super("history", new String[] { "<player>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1 || commands.length == 0) {
            Command.sendMessage(ChatFormatting.RED + "Please specify a player.");
        }
        UUID uuid;
        try {
            uuid = PlayerUtil.getUUIDFromName(commands[0]);
        }
        catch (Exception e) {
            Command.sendMessage("An error occured.");
            return;
        }
        List<String> names;
        try {
            names = PlayerUtil.getHistoryOfNames(uuid);
        }
        catch (Exception e) {
            Command.sendMessage("An error occured.");
            return;
        }
        if (names != null) {
            Command.sendMessage(commands[0] + "\u00c2´s name history:");
            for (final String name : names) {
                Command.sendMessage(name);
            }
        }
        else {
            Command.sendMessage("No names found.");
        }
    }
}
