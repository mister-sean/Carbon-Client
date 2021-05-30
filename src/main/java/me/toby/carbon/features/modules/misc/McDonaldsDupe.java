package me.toby.carbon.features.modules.misc;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.Util;

public class CarbonDupe extends Module
{
    public Setting<String> discord;
    
    public CarbonDupe() {
        super("Advertise", "Advertises Carbon Client.", Category.MISC, true, false, false);
        this.discord = (Setting<String>)this.register(new Setting("Discord", (T)"https://discord.gg/snDa88Vjfz"));
    }
    
    @Override
    public void onEnable() {
        if (Util.mc.player != null) {
            Util.mc.player.sendChatMessage("Join Carbon Client Today at: " + this.discord.getValueAsString() + "!");
        }
        this.disable();
    }
}
