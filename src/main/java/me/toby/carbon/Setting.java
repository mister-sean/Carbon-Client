package me.toby.carbon;

import me.toby.carbon.carbon;
import me.toby.carbon.hack.Hack;

public class Setting<T> {

    private final String name;
    private final Hack parent;
    public T value;

    public Setting(String name, T value, Hack parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;

        Carbon.SETTINGS.addSetting(this);
    }

    public String getName() {
        return this.name;
    }

    public T getValue() {
        return this.value;
    }

    public String getType() {
    	return "";
    }

    public Hack getParent() {
        return this.parent;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
