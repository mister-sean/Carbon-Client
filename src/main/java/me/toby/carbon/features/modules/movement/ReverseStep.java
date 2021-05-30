package me.toby.carbon.features.modules.movement;

import me.toby.carbon.features.Feature;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;

public class ReverseStep extends Module
{
    private final Setting<Integer> speed;
    
    public ReverseStep() {
        super("ReverseStep", "Go down", Category.MOVEMENT, true, false, false);
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", 10, 1, 20));
    }
    
    @Override
    public void onUpdate() {
        if (Feature.fullNullCheck() || ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.isOnLadder()) {
            return;
        }
        if (ReverseStep.mc.player.onGround) {
            final EntityPlayerSP player2;
            final EntityPlayerSP player = player2 = ReverseStep.mc.player;
            player2.motionY -= this.speed.getValue() / 10.0f;
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        ReverseStep.mc.player.motionY = 0.0;
    }
}
