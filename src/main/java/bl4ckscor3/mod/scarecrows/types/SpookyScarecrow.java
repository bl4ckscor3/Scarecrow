package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpookyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ANA				- A: Arm
	 *   F				- N: Netherrack
	 *   				- F: Netherbrick Fence
	 */

	public SpookyScarecrow()
	{
		super(3, Configuration.spooky_scarecrow.RANGE, Configuration.spooky_scarecrow.SCARE_ANIMALS);
	}

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.NETHERRACK)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void destroy(World world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.down();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
		world.destroyBlock(pos.down(), false); //foot
	}
}
