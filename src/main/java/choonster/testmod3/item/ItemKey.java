package choonster.testmod3.item;

import choonster.testmod3.TestMod3;
import choonster.testmod3.api.capability.lock.ILock;
import choonster.testmod3.capability.lock.CapabilityLock;
import choonster.testmod3.network.MessageOpenLockGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

/**
 * A key that can lock {@link ILock}s.
 *
 * @author Choonster
 */
public class ItemKey extends ItemTestMod3 {
	public ItemKey() {
		super("key");
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		final ILock lock = CapabilityLock.getLock(worldIn, pos, facing);
		if (lock != null) {
			if (!worldIn.isRemote) {
				if (lock.isLocked()) {
					playerIn.addChatMessage(new TextComponentTranslation("testmod3:lock.already_locked"));
				} else {
					TestMod3.network.sendTo(new MessageOpenLockGui(pos, facing), (EntityPlayerMP) playerIn);
				}
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}
}
