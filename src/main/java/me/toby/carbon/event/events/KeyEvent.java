// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;

public class KeyEvent extends EventStage
{
    private final int key;
    
    public KeyEvent(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
