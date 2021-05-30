package me.toby.carbon;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;

import me.toby.carbon.manager.ColorManager;
import me.toby.carbon.manager.CommandManager;
import me.toby.carbon.manager.ConfigManager;
import me.toby.carbon.manager.EventManager;
import me.toby.carbon.manager.FileManager;
import me.toby.carbon.manager.FriendManager;
import me.toby.carbon.manager.HoleManager;
import me.toby.carbon.manager.InventoryManager;
import me.toby.carbon.manager.ModuleManager;
import me.toby.carbon.manager.PacketManager;
import me.toby.carbon.manager.PositionManager;
import me.toby.carbon.manager.PotionManager;
import me.toby.carbon.manager.ReloadManager;
import me.toby.carbon.manager.RotationManager;
import me.toby.carbon.manager.ServerManager;
import me.toby.carbon.manager.SpeedManager;
import me.toby.carbon.manager.TextManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "carbonclient", name = "Carbon Client", version = "alpha-0.0.01")
public class Carbon
{
    public static final String MODID = "carbonclient";
    public static final String MODNAME = "Carbon Client";
    public static final String MODVER = "alpha-0.0.01";
    public static final Logger LOGGER;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    @Mod.Instance
    public static Carbon INSTANCE;
    private static boolean unloaded;
    
    public static void load() {
        Carbon.LOGGER.info("\n\nLoading Carbon Client by Toby & noat");
        Carbon.unloaded = false;
        if (Carbon.reloadManager != null) {
            Carbon.reloadManager.unload();
            Carbon.reloadManager = null;
        }
        Carbon.textManager = new TextManager();
        Carbon.commandManager = new CommandManager();
        Carbon.friendManager = new FriendManager();
        Carbon.moduleManager = new ModuleManager();
        Carbon.rotationManager = new RotationManager();
        Carbon.packetManager = new PacketManager();
        Carbon.eventManager = new EventManager();
        Carbon.speedManager = new SpeedManager();
        Carbon.potionManager = new PotionManager();
        Carbon.inventoryManager = new InventoryManager();
        Carbon.serverManager = new ServerManager();
        Carbon.fileManager = new FileManager();
        Carbon.colorManager = new ColorManager();
        Carbon.positionManager = new PositionManager();
        Carbon.configManager = new ConfigManager();
        Carbon.holeManager = new HoleManager();
        Carbon.LOGGER.info("Managers loaded.");
        Carbon.moduleManager.init();
        Carbon.LOGGER.info("Modules loaded.");
        Carbon.configManager.init();
        Carbon.eventManager.init();
        Carbon.LOGGER.info("EventManager loaded.");
        Carbon.textManager.init(true);
        Carbon.moduleManager.onLoad();
        Carbon.LOGGER.info("Carbon Client successfully loaded!\n");
    }
    
    public static void unload(final boolean unload) {
        Carbon.LOGGER.info("\n\nUnloading Carbon Client by zPrestige_ & ad6q");
        if (unload) {
            (Carbon.reloadManager = new ReloadManager()).init((Carbon.commandManager != null) ? Carbon.commandManager.getPrefix() : ".");
        }
        onUnload();
        Carbon.eventManager = null;
        Carbon.friendManager = null;
        Carbon.speedManager = null;
        Carbon.holeManager = null;
        Carbon.positionManager = null;
        Carbon.rotationManager = null;
        Carbon.configManager = null;
        Carbon.commandManager = null;
        Carbon.colorManager = null;
        Carbon.serverManager = null;
        Carbon.fileManager = null;
        Carbon.potionManager = null;
        Carbon.inventoryManager = null;
        Carbon.moduleManager = null;
        Carbon.textManager = null;
        Carbon.LOGGER.info("Carbon Client unloaded!\n");
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    public static void onUnload() {
        if (!Carbon.unloaded) {
            Carbon.eventManager.onUnload();
            Carbon.moduleManager.onUnload();
            Carbon.configManager.saveConfig(Carbon.configManager.config.replaceFirst("Carbon/", ""));
            Carbon.moduleManager.onUnloadPost();
            Carbon.unloaded = true;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Carbon.LOGGER.info("Carbon Client");
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("Carbon Client alpha-0.0.01");
        load();
    }
    
    static {
        LOGGER = LogManager.getLogger("Carbon Client");
        Carbon.unloaded = false;
    }
}
