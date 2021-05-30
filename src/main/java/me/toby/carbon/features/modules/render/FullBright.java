package me.toby.carbon.features.modules.render;

import me.toby.carbon.features.modules.Module;

public class FullBright extends Module
{
    float oldBrightness;
    
    public FullBright() {
        super("Fullbright", "Permanent brightness", Category.RENDER, true, false, false);
    }
    
    @Override
    public void onEnable() {
        this.oldBrightness = FullBright.mc.gameSettings.gammaSetting;
        FullBright.mc.gameSettings.gammaSetting = 1000.0f;
    }
    
    @Override
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = this.oldBrightness;
    }
    
    @Override
    public void onUpdate() {
        if (FullBright.mc.gameSettings.gammaSetting != 1000.0f) {
            FullBright.mc.gameSettings.gammaSetting = 1000.0f;
        }
    }
}
