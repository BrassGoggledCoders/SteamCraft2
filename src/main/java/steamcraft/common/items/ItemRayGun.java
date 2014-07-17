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
package steamcraft.common.items;

/**
 * @author Surseance
 *
 */
import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import steamcraft.common.Steamcraft;
import steamcraft.common.lib.LibInfo;
import boilerplate.common.utils.PlayerUtils;
import boilerplate.common.utils.Utils;
import cofh.api.energy.IEnergyContainerItem;

public class ItemRayGun extends BaseItem implements IEnergyContainerItem
{
	int energyPerUse = 500;
	int maxEnergy = 800000;
	int maxReceive = 50;

	private Random random = new Random();

	static HashMap<String, Object> ray = new HashMap<String, Object>();
	static HashMap<String, Long> soundDelay = new HashMap<String, Long>();

	public ItemRayGun(String raySound)
	{
		super();
		this.setMaxStackSize(1);
		this.setFull3D();
	}

	@SuppressWarnings("all")
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(this.getEnergyStored(stack) > energyPerUse)
		{
			MovingObjectPosition mop = PlayerUtils.getTargetBlock(world, player, true, 20);

			Vec3 vec3 = player.getLookVec();
			double tx = player.posX + vec3.xCoord * 10.0D;
			double ty = player.posY + vec3.yCoord * 10.0D;
			double tz = player.posZ + vec3.zCoord * 10.0D;
			int impact = 0;

			if (mop != null) // Sets vector
			{
				tx = mop.hitVec.xCoord;
				ty = mop.hitVec.yCoord;
				tz = mop.hitVec.zCoord;
				impact = 5;
			}

			if (soundDelay.get(player) == null)
				soundDelay.put(player.getCommandSenderName(), Long.valueOf(0L));

			if (!world.isRemote && soundDelay.get(player.getCommandSenderName()).longValue() < System.currentTimeMillis())
			{
				world.playSoundEffect(tx, ty, tz, LibInfo.PREFIX + "raygun", 0.35F, 1.0F);
				soundDelay.put(player.getCommandSenderName(), Long.valueOf(System.currentTimeMillis() + 1200L));
			}
			else
				soundDelay.put(player.getCommandSenderName(), Long.valueOf(0L));
			if (world.isRemote)
				ray.put(player.getCommandSenderName(),
						Steamcraft.proxy.rayFX(world, player, tx, ty, tz, 2, false, impact > 0 ? 2.0F : 0.0F, ray.get(player), impact, Color.GREEN));

			// Couldn't get this shit to work...maybe I was just having a bad day
			/*
			 * if(mop != null && mop.typeOfHit == EnumMovingObjectType.ENTITY) { if(!(mop.entityHit instanceof EntityEnderman)) { //mop.entityHit.setFire(500);
			 * System.out.println(mop.entityHit); //DamageSource damage = DamageSource.causePlayerDamage(player); extractEnergy(stack, energyPerUse, false); } }
			 */
			if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				// this.spawnParticles(world, x, y, z);

				if (!world.isRemote && !world.isAirBlock(x, y, z)
						&& !Utils.getBlockUnbreakable(world, x, y, z))
					for (int i = x - this.random.nextInt(3); i < x + this.random.nextInt(3); i++)
						for (int j = y - this.random.nextInt(3); j < y + this.random.nextInt(3); j++)
							for (int k = z - this.random.nextInt(3); k < z + this.random.nextInt(3); k++)
							{
								if (world.isAirBlock(i, j, k))
								{
									world.setBlock(i, j, k, Blocks.fire);
									extractEnergy(stack, energyPerUse, false);
								}
								else if (world.getBlock(i, j, k) == Blocks.snow)
								{
									world.setBlock(i, j, k, Blocks.flowing_water);
									extractEnergy(stack, energyPerUse, false);
								}
								if (world.getBlock(i, j, k) == Blocks.snow_layer)
								{
									world.setBlock(i, j, k, Blocks.flowing_water);
									extractEnergy(stack, energyPerUse, false);
								}
								if (world.getBlock(i, j, k) == Blocks.sand)
								{
									world.setBlock(i, j, k, Blocks.glass);
									extractEnergy(stack, energyPerUse, false);
								}
								if (world.getBlock(i, j, k) == Blocks.netherrack)
								{
									world.setBlock(i, j, k, Blocks.flowing_lava);
									extractEnergy(stack, energyPerUse, false);
								}
							}
			}
		}
		return stack;
	}
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(maxEnergy - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.energyPerUse, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container)
	{
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container)
	{
		return this.maxEnergy;
	}

}