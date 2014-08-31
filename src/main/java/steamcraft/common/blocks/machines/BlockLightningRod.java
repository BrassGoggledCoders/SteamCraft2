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
package steamcraft.common.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.client.lib.RenderIDs;
import steamcraft.common.InitBlocks;
import steamcraft.common.blocks.BlockDummy;
import steamcraft.common.tiles.TileLightningRod;

public class BlockLightningRod extends BaseContainerBlock
{

	public BlockLightningRod(Material mat)
	{
		super(mat);
		this.setBlockBounds(0.3F, 0, 0.3F, 0.7F, 1.0F, 0.7F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileLightningRod();
	}

	@Override
	public int getRenderType()
	{
		return RenderIDs.blockLightningRodRI;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	/*public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		world.setBlock(x, y+1, z, InitBlocks.blockDummy);
	}
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(block.isAir(world, x, y, z))
		{
			this.breakBlock(world, x, y, z, this, 0);
		}
	}*/
}
