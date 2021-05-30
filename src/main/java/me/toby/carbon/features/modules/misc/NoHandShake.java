// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.Unpooled;
import me.toby.carbon.event.events.PacketEvent;
import me.toby.carbon.features.modules.Module;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class NoHandShake extends Module
{
    public NoHandShake() {
        super("NoHandshake", "Doesn't send your mod list to the server :troll:.", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof FMLProxyPacket && !NoHandShake.mc.isSingleplayer()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketCustomPayload) {
            final CPacketCustomPayload packet = event.getPacket();
            if (packet.getChannelName().equals("MC|Brand")) {
                packet.data = new PacketBuffer(Unpooled.buffer()).writeString("vanilla");
            }
        }
    }
}
