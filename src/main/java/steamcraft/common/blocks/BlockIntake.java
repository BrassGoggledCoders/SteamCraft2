package steamcraft.common.blocks;

import steamcraft.common.blocks.machine.BlockContainerMod;
import steamcraft.common.tiles.TileIntake;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockIntake extends BlockContainerMod {

	public BlockIntake(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileIntake();
	}

}
