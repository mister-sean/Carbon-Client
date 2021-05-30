package me.toby.carbon.features.gui.components.items.buttons;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.gui.CarbonGui;
import me.toby.carbon.features.modules.client.ClickGui;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.RenderUtil;
import me.toby.carbon.util.Util;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class BooleanButton extends Button
{
    private final Setting setting;
    
    public BooleanButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? Carbon.colorManager.getColorWithAlpha(Carbon.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : Carbon.colorManager.getColorWithAlpha(Carbon.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        Carbon.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 1.7f - CarbonGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }
    
    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            Util.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    @Override
    public void toggle() {
        this.setting.setValue(!this.setting.getValue());
    }
    
    @Override
    public boolean getState() {
        return this.setting.getValue();
    }
}
