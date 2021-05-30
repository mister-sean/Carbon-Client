package me.toby.carbon.features.modules.player;

import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;

public class TpsSync extends Module
{
    private static TpsSync INSTANCE;
    public Setting<Boolean> attack;
    public Setting<Boolean> mining;
    
    public TpsSync() {
        super("TpsSync", "Syncs your client with the TPS.", Category.PLAYER, true, true, false);
        this.attack = (Setting<Boolean>)this.register(new Setting("Attack", Boolean.FALSE));
        this.mining = (Setting<Boolean>)this.register(new Setting("Mine", Boolean.TRUE));
        this.setInstance();
    }
    
    public static TpsSync getInstance() {
        if (TpsSync.INSTANCE == null) {
            TpsSync.INSTANCE = new TpsSync();
        }
        return TpsSync.INSTANCE;
    }
    
    private void setInstance() {
        TpsSync.INSTANCE = this;
    }
    
    static {
        TpsSync.INSTANCE = new TpsSync();
    }
}
