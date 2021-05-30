package me.toby.carbon.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumFacing;
import me.toby.carbon.event.events.PacketEvent;
import me.toby.carbon.features.modules.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class BuildHeight2 extends Module
{
    public BuildHeight2() {
        super("BuildHeight", "Allows you to place at build height", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketPlayerTryUseItemOnBlock packet;
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && (packet = event.getPacket()).getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
            packet.placedBlockDirection = EnumFacing.DOWN;
        }
    }
}
