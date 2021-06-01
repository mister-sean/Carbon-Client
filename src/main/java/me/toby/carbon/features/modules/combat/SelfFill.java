package me.toby.carbon.features.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import java.lang.reflect.Field;

import me.toby.carbon.Carbon;
import me.toby.carbon.event.events.PlayerJumpEvent;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.modules.movement.ReverseStep;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.manager.Mapping;
import me.toby.carbon.util.InventoryUtil;
import me.toby.carbon.util.WorldUtil;

public class SelfFill extends Module {
    private BlockPos playerPos;
    private final Setting<Boolean> timerfill = register(new Setting("TimerFill", false));
    private final Setting<Boolean> toggleRStep = register(new Setting("ToggleRStep", true));

    public SelfFill() {
        super("SelfFill", "SelfFills yourself in a hole.", Module.Category.COMBAT, true, false, true);
    }

    @Override
    public void onEnable() {

        if (timerfill.getValue()) {
            setTimer(50.0f);
        }
        if (toggleRStep.getValue()) {
            Carbon.moduleManager.getModuleByName("ReverseStep").isEnabled(); {
                ReverseStep.getInstance().disable();
            }
        }
        playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (mc.world.getBlockState(playerPos).getBlock().equals(Blocks.OBSIDIAN)) {
            disable();
            return;
        }
        mc.player.jump();
    }

    @Override
    public void onDisable() {
        if (toggleRStep.getValue()) {
            Carbon.moduleManager.getModuleByName("ReverseStep").isDisabled(); {
                ReverseStep.getInstance().enable();
            }
        }
        setTimer(1.0f);
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        if (mc.player.posY > playerPos.getY() + 1.04) {
            WorldUtil.placeBlock(playerPos, InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN));
            mc.player.jump();
            disable();
        }
    }

    private void setTimer(final float value) {
        try {
            final Field timer = Minecraft.class.getDeclaredField(Mapping.timer);
            timer.setAccessible(true);
            final Field tickLength = net.minecraft.util.Timer.class.getDeclaredField(Mapping.tickLength);
            tickLength.setAccessible(true);
            tickLength.setFloat(timer.get(mc), 50.0f / value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDisplayInfo() {
        if (timerfill.getValue()) {
            return "Timer";
        } else {
            return "Burrow";
        }
    }

}