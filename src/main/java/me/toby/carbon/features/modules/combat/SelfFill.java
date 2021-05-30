// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.modules.combat;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.util.Util;

public class SelfFill extends Module
{
    public SelfFill() {
        super("SelfFill", "SelfFills yourself in a hole.", Category.COMBAT, true, true, true);
    }
    
    @Override
    public void onEnable() {
        if (Util.mc.player != null) {
            this.disable();
        }
    }
}
