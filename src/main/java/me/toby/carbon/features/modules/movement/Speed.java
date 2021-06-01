package me.toby.carbon.features.modules.movement;

import me.toby.carbon.features.modules.Module;

public class Speed extends Module {
    public Speed() {
        super ("Speed", "placeholder", Category.MOVEMENT, false, false, false);
    }

    @Override
    public String getDisplayInfo() {
        return "Strafe";
    }
}
