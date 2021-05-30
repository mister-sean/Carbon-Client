package me.toby.carbon.features.command.commands;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.command.Command;
import me.toby.carbon.manager.FriendManager;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", new String[] { "<add/del/name/clear>", "<name>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Carbon.friendManager.getFriends().isEmpty()) {
                Command.sendMessage("Friend list empty D:.");
            }
            else {
                String f = "Friends: ";
                for (final FriendManager.Friend friend : Carbon.friendManager.getFriends()) {
                    try {
                        f = f + friend.getUsername() + ", ";
                    }
                    catch (Exception ex) {}
                }
                Command.sendMessage(f);
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        Carbon.friendManager.addFriend(commands[1]);
                        Command.sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                    }
                    case "del": {
                        Carbon.friendManager.removeFriend(commands[1]);
                        Command.sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    }
                    default: {
                        Command.sendMessage("Unknown Command, try friend add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        final String s2 = commands[0];
        switch (s2) {
            case "reset": {
                Carbon.friendManager.onLoad();
                Command.sendMessage("Friends got reset.");
            }
            default: {
                Command.sendMessage(commands[0] + (Carbon.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            }
        }
    }
}
