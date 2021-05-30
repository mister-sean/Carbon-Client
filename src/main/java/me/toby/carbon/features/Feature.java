package me.toby.carbon.features;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.gui.McDonaldsGui;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.manager.TextManager;
import me.toby.carbon.util.Util;

public class Feature implements Util
{
    public List<Setting> settings;
    public TextManager renderer;
    private String name;
    
    public Feature() {
        this.settings = new ArrayList<Setting>();
        this.renderer = McDonalds.textManager;
    }
    
    public Feature(final String name) {
        this.settings = new ArrayList<Setting>();
        this.renderer = McDonalds.textManager;
        this.name = name;
    }
    
    public static boolean nullCheck() {
        return Feature.mc.player == null;
    }
    
    public static boolean fullNullCheck() {
        return Feature.mc.player == null || Feature.mc.world == null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public boolean hasSettings() {
        return !this.settings.isEmpty();
    }
    
    public boolean isEnabled() {
        return this instanceof Module && ((Module)this).isOn();
    }
    
    public boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public Setting register(final Setting setting) {
        setting.setFeature(this);
        this.settings.add(setting);
        if (this instanceof Module && Feature.mc.currentScreen instanceof McDonaldsGui) {
            McDonaldsGui.getInstance().updateModule((Module)this);
        }
        return setting;
    }
    
    public void unregister(final Setting settingIn) {
        final ArrayList<Setting> removeList = new ArrayList<Setting>();
        for (final Setting setting : this.settings) {
            if (!setting.equals(settingIn)) {
                continue;
            }
            removeList.add(setting);
        }
        if (!removeList.isEmpty()) {
            this.settings.removeAll(removeList);
        }
        if (this instanceof Module && Feature.mc.currentScreen instanceof McDonaldsGui) {
            McDonaldsGui.getInstance().updateModule((Module)this);
        }
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting setting : this.settings) {
            if (!setting.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return setting;
        }
        return null;
    }
    
    public void reset() {
        for (final Setting setting : this.settings) {
            setting.setValue(setting.getDefaultValue());
        }
    }
    
    public void clearSettings() {
        this.settings = new ArrayList<Setting>();
    }
}
