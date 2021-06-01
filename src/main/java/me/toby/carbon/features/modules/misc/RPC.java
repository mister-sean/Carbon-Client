package me.toby.carbon.features.modules.misc;

import me.toby.carbon.DiscordPresence;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;

public class RPC extends Module
{
    public static RPC INSTANCE;
    public Setting<Boolean> showIP;
    public Setting<String> state;

    public RPC() {
        super("RPC", "Discord rich presence", Category.MISC, false, false, false);
        this.showIP = (Setting<Boolean>)this.register(new Setting("ShowIP", true, "Shows the server IP in your discord presence."));
        this.state = (Setting<String>)this.register(new Setting("State", "Zori 1.2.2", "Sets the state of the DiscordRPC."));
        RPC.INSTANCE = this;
    }

    @Override
    public void onEnable() {
        DiscordPresence.start();
    }

    @Override
    public void onDisable() {
        DiscordPresence.stop();
    }
}