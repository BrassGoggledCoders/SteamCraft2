package common.steamcraft.common.item;

import ic2.api.item.IBoxable;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import common.steamcraft.common.block.ModBlocks;
import common.steamcraft.common.lib2.CreativeTabsMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChisel extends ItemMod implements IBoxable
{
	private Random random = new Random();

	public ItemChisel(int id, int damage)
	{
		super(id);
		this.maxStackSize = 1;
		this.setMaxDamage(damage);
		this.setCreativeTab(CreativeTabsMod.tabSCEquipment);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int l, float f1, float f2, float f3)
	{
		int bid = world.getBlockId(i, j, k);

		if(bid == Block.blockIron.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorIron.blockID);
		}
		if(bid == Block.blockGold.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorGold.blockID);
		}
		if(bid == Block.blockDiamond.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorDiamond.blockID);
		}
		if(bid == ModBlocks.blockCastIron.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorCastIron.blockID);
		}
		if(bid == ModBlocks.blockBrass.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorBrass.blockID);
		}
		if(bid == ModBlocks.blockVolucite.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorVolucite.blockID);
		}
		if(bid == Block.blockLapis.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorLapis.blockID);
		}
		if(bid == Block.stone.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorStone.blockID);
		}
		if(bid == ModBlocks.blockUranium.blockID)
		{
			world.setBlock(i, j, k, ModBlocks.decorUranium.blockID);
		}

		double d0 = world.rand.nextDouble();
		int i1 = i + world.rand.nextInt(16) - world.rand.nextInt(16);
		int j1 = j + world.rand.nextInt(8) - world.rand.nextInt(8);
		int k1 = k + world.rand.nextInt(16) - world.rand.nextInt(16);
		//float f6 = (world.rand.nextFloat() - 0.5F) * 0.2F;
		float f4 = (world.rand.nextFloat() - 0.5F) * 0.2F;
		float f5 = (world.rand.nextFloat() - 0.5F) * 0.2F;
		double d1 = i1 + (i - i1) * d0 + (world.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
		double d2 = j1 + (j - j1) * d0 + world.rand.nextDouble() * 1.0D - 0.5D;
		double d3 = k1 + (k - k1) * d0 + (world.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
		world.spawnParticle("portal", d1, d2, d3, f3, f4, f5);
		world.spawnParticle("smoke", i, j, k, random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
		stack.damageItem(1, player);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}
}