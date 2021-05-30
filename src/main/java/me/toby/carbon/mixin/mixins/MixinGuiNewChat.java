package me.toby.carbon.mixin.mixins;

import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat extends Gui
{
}
