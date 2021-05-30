package me.toby.carbon.manager;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.event.events.ConnectionEvent;
import me.toby.carbon.event.events.DeathEvent;
import me.toby.carbon.event.events.PacketEvent;
import me.toby.carbon.event.events.Render2DEvent;
import me.toby.carbon.event.events.Render3DEvent;
import me.toby.carbon.event.events.TotemPopEvent;
import me.toby.carbon.event.events.UpdateWalkingPlayerEvent;
import me.toby.carbon.features.Feature;
import me.toby.carbon.features.command.Command;
import me.toby.carbon.features.modules.client.HUD;
import me.toby.carbon.features.modules.misc.PopCounter;
import me.toby.carbon.util.Timer;
import net.minecraftforge.client.event.ClientChatEvent;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.UUID;
import net.minecraft.network.play.server.SPacketTimeUpdate;

import com.google.common.base.Strings;
import java.util.function.Predicate;
import java.util.Objects;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.Iterator;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventManager extends Feature
{
    private final Timer logoutTimer;
    
    public EventManager() {
        this.logoutTimer = new Timer();
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onUnload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!Feature.fullNullCheck() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals((Object)EventManager.mc.player)) {
            Carbon.inventoryManager.update();
            Carbon.moduleManager.onUpdate();
            if (HUD.getInstance().renderingMode.getValue() == HUD.RenderingMode.Length) {
                Carbon.moduleManager.sortModules(true);
            }
            else {
                Carbon.moduleManager.sortModulesABC();
            }
        }
    }
    
    @SubscribeEvent
    public void onClientConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.logoutTimer.reset();
        Carbon.moduleManager.onLogin();
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Carbon.moduleManager.onLogout();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (fullNullCheck()) {
            return;
        }
        Carbon.moduleManager.onTick();
        for (final EntityPlayer player : EventManager.mc.world.playerEntities) {
            if (player != null) {
                if (player.getHealth() > 0.0f) {
                    continue;
                }
                MinecraftForge.EVENT_BUS.post((Event)new DeathEvent(player));
                PopCounter.getInstance().onDeath(player);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getStage() == 0) {
            Carbon.speedManager.updateValues();
            Carbon.rotationManager.updateRotations();
            Carbon.positionManager.updatePosition();
        }
        if (event.getStage() == 1) {
            Carbon.rotationManager.restoreRotations();
            Carbon.positionManager.restorePosition();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() != 0) {
            return;
        }
        Carbon.serverManager.onPacketReceived();
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity((World)EventManager.mc.world) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)packet.getEntity((World)EventManager.mc.world);
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player));
                PopCounter.getInstance().onTotemPop(player);
            }
        }
        if (event.getPacket() instanceof SPacketPlayerListItem && !Feature.fullNullCheck() && this.logoutTimer.passedS(1.0)) {
            final SPacketPlayerListItem packet2 = event.getPacket();
            if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals((Object)packet2.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals((Object)packet2.getAction())) {
                return;
            }
            final UUID id;
            final SPacketPlayerListItem sPacketPlayerListItem;
            final String name;
            final EntityPlayer entity;
            String logoutName;
            packet2.getEntries().stream().filter(Objects::nonNull).filter(data -> !Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null).forEach(data -> {
                id = data.getProfile().getId();
                switch (sPacketPlayerListItem.getAction()) {
                    case ADD_PLAYER: {
                        name = data.getProfile().getName();
                        MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(0, id, name));
                        break;
                    }
                    case REMOVE_PLAYER: {
                        entity = EventManager.mc.world.getPlayerEntityByUUID(id);
                        if (entity != null) {
                            logoutName = entity.getName();
                            MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(1, entity, id, logoutName));
                            break;
                        }
                        else {
                            MinecraftForge.EVENT_BUS.post((Event)new ConnectionEvent(2, id, null));
                            break;
                        }
                        break;
                    }
                }
                return;
            });
        }
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            Carbon.serverManager.update();
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        EventManager.mc.profiler.startSection("Carbon");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        Carbon.moduleManager.onRender3D(render3dEvent);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        EventManager.mc.profiler.endSection();
    }
    
    @SubscribeEvent
    public void renderHUD(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            Carbon.textManager.updateResolution();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            final ScaledResolution resolution = new ScaledResolution(EventManager.mc);
            final Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
            Carbon.moduleManager.onRender2D(render2DEvent);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Carbon.moduleManager.onKeyPressed(Keyboard.getEventKey());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(final ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                EventManager.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    Carbon.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                }
                else {
                    Command.sendMessage("Please enter a command.");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(ChatFormatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }
}
