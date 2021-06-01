package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;

public class KeyPressedEvent
        extends EventStage {
    public boolean info;
    public boolean pressed;

    public KeyPressedEvent(boolean info, boolean pressed) {
        this.info = info;
        this.pressed = pressed;
    }
}

