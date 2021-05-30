package me.toby.carbon.features.gui;

import java.io.IOException;
import org.lwjgl.input.Mouse;

import me.toby.carbon.Carbon;
import me.toby.carbon.features.Feature;
import me.toby.carbon.features.gui.components.Component;
import me.toby.carbon.features.gui.components.items.Item;
import me.toby.carbon.features.gui.components.items.buttons.Button;
import me.toby.carbon.features.gui.components.items.buttons.ModuleButton;
import me.toby.carbon.features.modules.Module;

import java.util.Iterator;
import java.util.function.Function;
import java.util.Comparator;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class CarbonGui extends GuiScreen
{
    private static CarbonGui oyveyGui;
    private static CarbonGui INSTANCE;
    private final ArrayList<Component> components;
    
    public CarbonGui() {
        this.components = new ArrayList<Component>();
        this.setInstance();
        this.load();
    }
    
    public static CarbonGui getInstance() {
        if (CarbonGui.INSTANCE == null) {
            CarbonGui.INSTANCE = new CarbonGui();
        }
        return CarbonGui.INSTANCE;
    }
    
    public static CarbonGui getClickGui() {
        return getInstance();
    }
    
    private void setInstance() {
        CarbonGui.INSTANCE = this;
    }
    
    private void load() {
        int x = -84;
        for (final Module.Category category : Carbon.moduleManager.getCategories()) {
            final ArrayList<Component> components2 = this.components;
            final String name = category.getName();
            x += 90;
            components2.add(new Component(name, x, 4, true) {
                @Override
                public void setupItems() {
                    CarbonGui$1.counter1 = new int[] { 1 };
                    Carbon.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing((Function<? super Item, ? extends Comparable>)Feature::getName)));
    }
    
    public void updateModule(final Module module) {
        for (final Component component : this.components) {
            for (final Item item : component.getItems()) {
                if (!(item instanceof ModuleButton)) {
                    continue;
                }
                final ModuleButton button = (ModuleButton)item;
                final Module mod = button.getModule();
                if (module == null) {
                    continue;
                }
                if (!module.equals(mod)) {
                    continue;
                }
                button.initSettings();
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel();
        this.drawDefaultBackground();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        }
        else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return component;
        }
        return null;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
    
    static {
        CarbonGui.INSTANCE = new CarbonGui();
    }
}
