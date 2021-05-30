package me.toby.carbon.features.modules.client;

import me.toby.carbon.Discord;
import me.toby.carbon.features.modules.Module;

public class RPC extends Module
{
    public static RPC INSTANCE;
    
    public RPC() {
        super("RPC", "Discord rich presence", Category.CLIENT, false, false, false);
        RPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Discord.start();
    }
    
    @Override
    public void onDisable() {
        Discord.stop();
    }
    
    public void onClientDisconnect() {
        this.disable();
    }
    
    public void onClientStartup() {
        this.enable();
    }
}
