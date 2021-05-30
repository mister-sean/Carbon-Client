package me.toby.carbon.features.modules.combat;

import java.util.Iterator;

import me.toby.carbon.Carbon;
import me.toby.carbon.event.events.UpdateWalkingPlayerEvent;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.DamageUtil;
import me.toby.carbon.util.EntityUtil;
import me.toby.carbon.util.MathUtil;
import me.toby.carbon.util.Timer;
import me.toby.carbon.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;

public class Killaura extends Module
{
    public static Entity target;
    private final Timer timer;
    public Setting<Float> range;
    public Setting<Boolean> delay;
    public Setting<Boolean> rotate;
    public Setting<Boolean> onlySharp;
    public Setting<Float> raytrace;
    public Setting<Boolean> players;
    public Setting<Boolean> mobs;
    public Setting<Boolean> animals;
    public Setting<Boolean> vehicles;
    public Setting<Boolean> projectiles;
    public Setting<Boolean> tps;
    public Setting<Boolean> packet;
    
    public Killaura() {
        super("Killaura", "Kills aura.", Category.COMBAT, true, false, false);
        this.timer = new Timer();
        this.range = (Setting<Float>)this.register(new Setting("Range", 6.0f, 0.1f, 7.0f));
        this.delay = (Setting<Boolean>)this.register(new Setting("HitDelay", true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", true));
        this.onlySharp = (Setting<Boolean>)this.register(new Setting("SwordOnly", true));
        this.raytrace = (Setting<Float>)this.register(new Setting("Raytrace", 6.0f, 0.1f, 7.0f, "Wall Range."));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", true));
        this.mobs = (Setting<Boolean>)this.register(new Setting("Mobs", false));
        this.animals = (Setting<Boolean>)this.register(new Setting("Animals", false));
        this.vehicles = (Setting<Boolean>)this.register(new Setting("Entities", false));
        this.projectiles = (Setting<Boolean>)this.register(new Setting("Projectiles", false));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", true));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", false));
    }
    
    @Override
    public void onTick() {
        if (!this.rotate.getValue()) {
            this.doKillaura();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && this.rotate.getValue()) {
            this.doKillaura();
        }
    }
    
    private void doKillaura() {
        if (this.onlySharp.getValue() && !EntityUtil.holdingWeapon((EntityPlayer)Util.mc.player)) {
            Killaura.target = null;
            return;
        }
        final int wait = this.delay.getValue() ? ((int)(DamageUtil.getCooldownByWeapon((EntityPlayer)Util.mc.player) * (this.tps.getValue() ? Carbon.serverManager.getTpsFactor() : 1.0f))) : 0;
        if (!this.timer.passedMs(wait)) {
            return;
        }
        Killaura.target = this.getTarget();
        if (Killaura.target == null) {
            return;
        }
        if (this.rotate.getValue()) {
            Carbon.rotationManager.lookAtEntity(Killaura.target);
        }
        EntityUtil.attackEntity(Killaura.target, this.packet.getValue(), true);
        this.timer.reset();
    }
    
    private Entity getTarget() {
        Entity target = null;
        double distance = this.range.getValue();
        double maxHealth = 36.0;
        for (final Entity entity : Util.mc.world.playerEntities) {
            if ((this.players.getValue() && entity instanceof EntityPlayer) || (this.animals.getValue() && EntityUtil.isPassive(entity)) || (this.mobs.getValue() && EntityUtil.isMobAggressive(entity)) || (this.vehicles.getValue() && EntityUtil.isVehicle(entity)) || (this.projectiles.getValue() && EntityUtil.isProjectile(entity))) {
                if (entity instanceof EntityLivingBase && EntityUtil.isntValid(entity, distance)) {
                    continue;
                }
                if (!Util.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && Util.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue())) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = Util.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
                else {
                    if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                        target = entity;
                        break;
                    }
                    if (Util.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = Util.mc.player.getDistanceSq(entity);
                        maxHealth = EntityUtil.getHealth(entity);
                    }
                    if (EntityUtil.getHealth(entity) >= maxHealth) {
                        continue;
                    }
                    target = entity;
                    distance = Util.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
            }
        }
        return target;
    }
    
    @Override
    public String getDisplayInfo() {
        if (Killaura.target instanceof EntityPlayer) {
            return Killaura.target.getName();
        }
        return null;
    }
}
