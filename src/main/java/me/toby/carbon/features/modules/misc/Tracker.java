// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import me.toby.carbon.event.events.DeathEvent;
import me.toby.carbon.features.command.Command;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.modules.client.HUD;
import me.toby.carbon.util.EntityUtil;
import me.toby.carbon.util.TextUtil;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Tracker extends Module
{
    private static Tracker instance;
    private EntityPlayer trackedPlayer;
    private int usedExp;
    private int usedStacks;
    
    public Tracker() {
        super("Duel", "Tracks players in 1v1s.", Category.MISC, true, false, false);
        this.usedExp = 0;
        this.usedStacks = 0;
        Tracker.instance = this;
    }
    
    public static Tracker getInstance() {
        if (Tracker.instance == null) {
            Tracker.instance = new Tracker();
        }
        return Tracker.instance;
    }
    
    @Override
    public void onUpdate() {
        if (this.trackedPlayer == null) {
            this.trackedPlayer = EntityUtil.getClosestEnemy(1000.0);
        }
        else if (this.usedStacks != this.usedExp / 64) {
            this.usedStacks = this.usedExp / 64;
            Command.sendMessage(TextUtil.coloredString(this.trackedPlayer.getName() + " has used " + this.usedStacks + " stacks of XP!", HUD.getInstance().commandColor.getValue()));
        }
    }
    
    public void onSpawnEntity(final Entity entity) {
        if (entity instanceof EntityExpBottle && Objects.equals(Tracker.mc.world.getClosestPlayerToEntity(entity, 3.0), this.trackedPlayer)) {
            ++this.usedExp;
        }
    }
    
    @Override
    public void onDisable() {
        this.trackedPlayer = null;
        this.usedExp = 0;
        this.usedStacks = 0;
    }
    
    @SubscribeEvent
    public void onDeath(final DeathEvent event) {
        if (event.player.equals((Object)this.trackedPlayer)) {
            this.usedExp = 0;
            this.usedStacks = 0;
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.trackedPlayer != null) {
            return this.trackedPlayer.getName();
        }
        return null;
    }
}
