package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;

public class Render3DEvent extends EventStage
{
    private float partialTicks;

    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

