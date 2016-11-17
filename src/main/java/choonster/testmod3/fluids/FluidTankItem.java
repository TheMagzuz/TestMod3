package choonster.testmod3.fluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An implementation of {@link IFluidHandlerItem} that stores its contents as a {@link FluidStack} in memory rather than
 * storing them in the vanilla {@link ItemStack} NBT.
 *
 * @author Choonster
 */
public class FluidTankItem extends FluidTank implements IFluidHandlerItem {
	private final ItemStack container;

	public FluidTankItem(ItemStack container, int capacity) {
		super(capacity);
		this.container = container;
	}

	public FluidTankItem(ItemStack container, @Nullable FluidStack fluidStack, int capacity) {
		super(fluidStack, capacity);
		this.container = container;
	}

	public FluidTankItem(ItemStack container, Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		this.container = container;
	}

	/**
	 * Get the container currently acted on by this fluid handler.
	 * The ItemStack may be different from its initial state, in the case of fluid containers that have different items
	 * for their filled and empty states.
	 * May be an empty item if the container was drained and is consumable.
	 */
	@Nonnull
	@Override
	public ItemStack getContainer() {
		return container;
	}
}
