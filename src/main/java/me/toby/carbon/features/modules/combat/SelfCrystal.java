


package me.toby.carbon.features.modules.combat;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.modules.combat.AutoCrystal;

public class SelfCrystal
extends Module {
    public SelfCrystal() {
        super("SelfCrystal", "Best module", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (AutoCrystal.getInstance().isEnabled()) {
            AutoCrystal.target = SelfCrystal.mc.player;
        }
    }
}

