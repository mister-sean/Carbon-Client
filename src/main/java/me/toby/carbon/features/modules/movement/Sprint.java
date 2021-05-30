package me.toby.carbon.features.modules.movement;

import me.toby.carbon.event.events.MoveEvent;
import me.toby.carbon.features.Feature;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sprint extends Module
{
    public Setting<Mode> mode;
    private static Sprint INSTANCE;
    
    public Sprint() {
        super("Sprint", "Modifies sprinting", Category.MOVEMENT, false, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", Mode.INSTANT));
        this.setInstance();
    }
    
    private void setInstance() {
        Sprint.INSTANCE = this;
    }
    
    public static Sprint getInstance() {
        if (Sprint.INSTANCE == null) {
            Sprint.INSTANCE = new Sprint();
        }
        return Sprint.INSTANCE;
    }
    
    @SubscribeEvent
    public void onSprint(final MoveEvent event) {
        if (event.getStage() == 1 && this.mode.getValue() == Mode.INSTANT && (Sprint.mc.player.movementInput.moveForward != 0.0f || Sprint.mc.player.movementInput.moveStrafe != 0.0f)) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case INSTANT: {
                if ((!Sprint.mc.gameSettings.keyBindForward.isKeyDown() && !Sprint.mc.gameSettings.keyBindBack.isKeyDown() && !Sprint.mc.gameSettings.keyBindLeft.isKeyDown() && !Sprint.mc.gameSettings.keyBindRight.isKeyDown()) || Sprint.mc.player.isSneaking() || Sprint.mc.player.collidedHorizontally) {
                    break;
                }
                if (Sprint.mc.player.getFoodStats().getFoodLevel() <= 6.0f) {
                    break;
                }
                Sprint.mc.player.setSprinting(true);
                break;
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (!Feature.nullCheck()) {
            Sprint.mc.player.setSprinting(false);
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    static {
        Sprint.INSTANCE = new Sprint();
    }
    
    public enum Mode
    {
        INSTANT;
    }
}
