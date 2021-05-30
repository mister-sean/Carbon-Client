// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class ChatEvent extends EventStage
{
    private final String msg;
    
    public ChatEvent(final String msg) {
        this.msg = msg;
    }
    
    public String getMsg() {
        return this.msg;
    }
}
