package noobanidus.mods.lootr;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import noobanidus.mods.lootr.commands.CommandBarrel;
import noobanidus.mods.lootr.commands.CommandChest;
import noobanidus.mods.lootr.commands.CommandLootr;
import noobanidus.mods.lootr.config.ConfigManager;
import noobanidus.mods.lootr.events.HandleBreak;
import noobanidus.mods.lootr.events.HandleMinecart;
import noobanidus.mods.lootr.init.ModBlocks;
import noobanidus.mods.lootr.init.ModTiles;
import noobanidus.mods.lootr.setup.CommonSetup;
import noobanidus.mods.lootr.setup.Setup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lootr")
public class Lootr {
  public static final Logger LOG = LogManager.getLogger();
  public static final String MODID = "lootr";
  public CommandLootr COMMAND_LOOTR;
  public CommandBarrel COMMAND_BARREL;
  public CommandChest COMMAND_CHEST;

  public Lootr() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);
    ConfigManager.loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Lootr.MODID + "-common.toml"));
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.addListener(HandleBreak::onBlockBreak);

    DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Setup::client);

    modBus.addListener(CommonSetup::init);

    modBus.addGenericListener(TileEntityType.class, ModTiles::registerTileEntityType);
    modBus.addGenericListener(Block.class, ModBlocks::registerBlocks);
    MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    MinecraftForge.EVENT_BUS.addListener(HandleMinecart::onEntityJoin);
  }

  public void onServerStarting(FMLServerStartingEvent event) {
    COMMAND_BARREL = new CommandBarrel(event.getCommandDispatcher());
    COMMAND_BARREL.register();
    COMMAND_CHEST = new CommandChest(event.getCommandDispatcher());
    COMMAND_CHEST.register();
    COMMAND_LOOTR = new CommandLootr(event.getCommandDispatcher());
    COMMAND_LOOTR.register();
  }
}
