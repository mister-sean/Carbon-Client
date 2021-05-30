// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class DeathEvent extends EventStage
{
    public EntityPlayer player;
    
    public DeathEvent(final EntityPlayer player) {
        this.player = player;
    }
}
