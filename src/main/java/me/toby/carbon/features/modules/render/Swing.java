package me.toby.carbon.features.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.toby.carbon.event.events.Packet;
import me.toby.carbon.features.Feature;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;

public class Swing extends Module
{
    private Setting<Hand> hand;
    
    public Swing() {
        super("06dModule", "Swing", Category.RENDER, false, false, false);
        this.hand = (Setting<Hand>)this.register(new Setting("Mode:", Hand.OFFHAND));
    }
    
    @Override
    public void onUpdate() {
        if (Swing.mc.world == null) {
            return;
        }
        if (this.hand.getValue().equals(Hand.OFFHAND)) {
            Swing.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (this.hand.getValue().equals(Hand.MAINHAND)) {
            Swing.mc.player.swingingHand = EnumHand.MAIN_HAND;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final Packet event) {
        if (Feature.nullCheck() || event.getType() == Packet.Type.INCOMING) {
            return;
        }
        if (event.getPacket() instanceof CPacketAnimation) {
            event.setCanceled(true);
        }
    }
    
    public enum Hand
    {
        OFFHAND, 
        MAINHAND;
    }
}
