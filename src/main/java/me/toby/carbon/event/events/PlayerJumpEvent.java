package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;

public class PlayerJumpEvent extends EventStage {
    public double motionX;
    public double motionY;

    public PlayerJumpEvent(double motionX, double motionY)
    {
        super();
        this.motionX = motionX;
        this.motionY = motionY;
    }
}