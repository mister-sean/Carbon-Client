package me.toby.carbon.features.modules.misc;

import java.util.Iterator;
import me.toby.carbon.features.command.*;
import net.minecraft.entity.passive.EntityDonkey;
import java.util.HashSet;
import net.minecraft.entity.Entity;
import java.util.Set;
import me.toby.carbon.features.modules.Module;

public class DonkeyNotify extends Module
{
    private static DonkeyNotify instance;
    private Set<Entity> entities;
    
    public DonkeyNotify() {
        super("DonkeyNotifier", "Notifies you when a donkey is discovered", Category.MISC, true, false, false);
        this.entities = new HashSet<Entity>();
        DonkeyNotify.instance = this;
    }
    
    @Override
    public void onEnable() {
        this.entities.clear();
    }
    
    @Override
    public void onUpdate() {
        for (final Entity entity : DonkeyNotify.mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey) {
                if (this.entities.contains(entity)) {
                    continue;
                }
                Command.sendSilentMessage("Donkey Detected at: " + entity.posX + "x, " + entity.posY + "y, " + entity.posZ + "z.");
                this.entities.add(entity);
            }
        }
    }
}