package com.choonster.testmod3.item;

import com.choonster.testmod3.TestMod3;
import com.choonster.testmod3.init.ModLootTables;
import com.choonster.testmod3.network.MessagePlayerReceivedLoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

/**
 * Gives the player random loot from a {@link LootTable} when they right click.
 *
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,37969.0.html
 *
 * @author Choonster
 */
public class ItemLootTableTest extends ItemTestMod3 {
	public ItemLootTableTest() {
		super("lootTableTest");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote) {
			LootTable lootTable = worldIn.getLootTableManager().getLootTableFromLocation(ModLootTables.LOOT_TABLE_TEST);

			LootContext lootContext = new LootContext.Builder((WorldServer) worldIn).withPlayer(playerIn).build();

			final List<ItemStack> itemStacks = lootTable.generateLootForPools(itemRand, lootContext);
			for (ItemStack itemStack : itemStacks){
				ItemHandlerHelper.giveItemToPlayer(playerIn, itemStack, 0);
			}

			playerIn.inventoryContainer.detectAndSendChanges();

			if (itemStacks.size() > 0) {
				TestMod3.network.sendTo(new MessagePlayerReceivedLoot(itemStacks), (EntityPlayerMP) playerIn);
			}else {
				playerIn.addChatComponentMessage(new TextComponentTranslation("message.testmod3:playerReceivedLoot.noLoot"));
			}
		}


		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}
}
