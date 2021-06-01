package me.toby.carbon.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.features.command.Command;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.EntityUtil;

public class NickHider extends Module {
    public final Setting<String> NameString = register(new Setting<Object>("Name", "New Name Here..."));
    private static NickHider instance;

    public NickHider() {
        super("NickHider", "Changes name", Module.Category.CLIENT, false, false, false);
        instance = this;
    }

    @Override
    public void onEnable() {
        Command.sendMessage(ChatFormatting.GRAY + "Success! Name succesfully changed to " + ChatFormatting.GREEN + NameString.getValue());
    }

    public static NickHider getInstance() {
        if (instance == null) {
            instance = new NickHider();
        }
        return instance;
    }
}