// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.modules.movement;

import me.toby.carbon.features.modules.Module;

public class Speed extends Module
{
    public Speed() {
        super("Speed", "Speed.", Category.MOVEMENT, true, true, false);
    }
    
    @Override
    public String getDisplayInfo() {
        return "Strafe";
    }
}
