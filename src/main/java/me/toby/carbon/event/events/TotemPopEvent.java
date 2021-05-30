// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopEvent extends EventStage
{
    private final EntityPlayer entity;
    
    public TotemPopEvent(final EntityPlayer entity) {
        this.entity = entity;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
}
