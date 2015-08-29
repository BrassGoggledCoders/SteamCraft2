package steamcraft.common.lib;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.player.FillBucketEvent;

/**
 * From Buildcraft :D
 */
public final class BucketHandler
{
	public static BucketHandler INSTANCE = new BucketHandler();
	public Map<Block, Item> buckets = new HashMap<Block, Item>();

	public BucketHandler()
	{
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
		ItemStack result = this.fillCustomBucket(event.world, event.target);

		if (result == null)
		{
			return;
		}

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
	{
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

		Item bucket = this.buckets.get(block);

		if ((bucket != null) && (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0))
		{
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		}
		else
		{
			return null;
		}
	}
}