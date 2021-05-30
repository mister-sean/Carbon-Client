// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.modules.player;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.util.InventoryUtil;
import net.minecraft.item.ItemExpBottle;

public class FastPlace extends Module
{
    public FastPlace() {
        super("FastExp", "Fast everything.", Category.PLAYER, true, false, false);
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class)) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
    }
}
