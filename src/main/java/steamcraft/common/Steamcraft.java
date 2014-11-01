/**
 * This class was created by BrassGoggledCoders modding team.
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 */
package steamcraft.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import steamcraft.client.GuiHandler;
import steamcraft.common.biomes.BiomeDepths;
import steamcraft.common.compat.CompatabilityLayer;
import steamcraft.common.config.Config;
import steamcraft.common.config.ConfigGeneral;
import steamcraft.common.config.ConfigWorldGen;
import steamcraft.common.dimension.WorldProviderDeeps;
import steamcraft.common.lib.CommandSteamcraft;
import steamcraft.common.lib.CreativeTabSteamcraft;
import steamcraft.common.lib.LibInfo;
import steamcraft.common.lib.events.EventHandlerClient;
import steamcraft.common.lib.events.EventHandlerFML;
import steamcraft.common.lib.events.EventHandlerForge;
import steamcraft.common.worldgen.WorldGenSteamcraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Surseance
 * 
 */
@Mod(modid = LibInfo.ID, name = LibInfo.NAME, version = LibInfo.VERSION, guiFactory = LibInfo.CONFIG_GUI, dependencies = "required-after:boilerplate")
public class Steamcraft
{
	@SidedProxy(clientSide = LibInfo.CLIENT_PROXY, serverSide = LibInfo.COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(LibInfo.ID)
	public static Steamcraft instance;

	public static CreativeTabs tabSC2 = new CreativeTabSteamcraft(CreativeTabs.getNextID(), "steamcraft");

	public static String configPath;

	public static BiomeGenBase biomeDepths;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LanguageRegistry.instance().getStringLocalization("itemGroup.steamcraft", "en_US");

		configPath = event.getModConfigurationDirectory() + "/sc2/";

		Config.initialise(configPath);

		if(ConfigWorldGen.generationEnabled)
			GameRegistry.registerWorldGenerator(new WorldGenSteamcraft(), 1);

		InitBlocks.init();
		InitItems.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		InitPackets.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		InitEntities.init();
		InitAchievements.init();

		proxy.registerDisplayInformation();
		proxy.registerRenderers();

		MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
		MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
		FMLCommonHandler.instance().bus().register(new EventHandlerFML());

		DimensionManager.registerProviderType(ConfigGeneral.deepsDimensionID, WorldProviderDeeps.class, false);
		DimensionManager.registerDimension(ConfigGeneral.deepsDimensionID, ConfigGeneral.deepsDimensionID);
		biomeDepths = new BiomeDepths(ConfigGeneral.depthsBiomeID);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		CompatabilityLayer.init();
		InitRecipes.init();
		final ModContainer container = FMLCommonHandler.instance().findContainerFor(this);
		LanguageRegistry.instance().loadLanguagesFor(container, Side.CLIENT);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSteamcraft());
	}
}
