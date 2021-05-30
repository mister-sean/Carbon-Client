package me.toby.carbon.features.modules.misc;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.Util;

public class McDonaldsDupe extends Module
{
    public Setting<String> discord;
    
    public McDonaldsDupe() {
        super("Advertise", "Advertises McDonalds Client.", Category.MISC, true, false, false);
        this.discord = (Setting<String>)this.register(new Setting("Discord", (T)"https://discord.gg/snDa88Vjfz"));
    }
    
    @Override
    public void onEnable() {
        if (Util.mc.player != null) {
            Util.mc.player.sendChatMessage("Join McDonalds Client Today at: " + this.discord.getValueAsString() + "!");
        }
        this.disable();
    }
}
