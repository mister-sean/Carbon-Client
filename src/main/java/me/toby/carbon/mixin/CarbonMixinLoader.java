package me.toby.carbon.mixin;

import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import me.toby.carbon.Carbon;

import org.spongepowered.asm.launch.MixinBootstrap;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class CarbonMixinLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public CarbonMixinLoader() {
        Carbon.LOGGER.info("\n\nLoading mixins by zPrestige_\n");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.Carbon.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        Carbon.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        CarbonMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        CarbonMixinLoader.isObfuscatedEnvironment = false;
    }
}
