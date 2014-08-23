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
package steamcraft.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import steamcraft.common.config.ConfigGeneral;
import cpw.mods.fml.client.config.GuiConfig;

public class SteamcraftConfigGui extends GuiConfig
{
	@SuppressWarnings("all")
	public SteamcraftConfigGui(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigGeneral.config.getCategory(ConfigGeneral.CATEGORY_GENERAL)).getChildElements(), "Steamcraft 2", false,
				false, GuiConfig.getAbridgedConfigPath(ConfigGeneral.config.toString()));
	}
}