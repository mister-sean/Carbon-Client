// 
// Decompiled by Procyon v0.5.36
// 

package me.toby.carbon.features.gui.components.items.buttons;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.gui.McDonaldsGui;
import me.toby.carbon.features.modules.client.ClickGui;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.RenderUtil;
import me.toby.carbon.util.Util;

public class UnlimitedSlider extends Button
{
    public Setting setting;
    
    public UnlimitedSlider(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.isHovering(mouseX, mouseY) ? McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()));
        McDonalds.textManager.drawStringWithShadow(" - " + this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue() + ChatFormatting.WHITE + " +", this.x + 2.3f, this.y - 1.7f - McDonaldsGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            Util.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            if (this.isRight(mouseX)) {
                if (this.setting.getValue() instanceof Double) {
                    this.setting.setValue(this.setting.getValue() + 1.0);
                }
                else if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(this.setting.getValue() + 1.0f);
                }
                else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue(this.setting.getValue() + 1);
                }
            }
            else if (this.setting.getValue() instanceof Double) {
                this.setting.setValue(this.setting.getValue() - 1.0);
            }
            else if (this.setting.getValue() instanceof Float) {
                this.setting.setValue(this.setting.getValue() - 1.0f);
            }
            else if (this.setting.getValue() instanceof Integer) {
                this.setting.setValue(this.setting.getValue() - 1);
            }
        }
    }
    
    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    @Override
    public void toggle() {
    }
    
    @Override
    public boolean getState() {
        return true;
    }
    
    public boolean isRight(final int x) {
        return x > this.x + (this.width + 7.4f) / 2.0f;
    }
}
