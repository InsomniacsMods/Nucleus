package net.insomniacs.nucleus.api.components.custom.bundle;

import net.minecraft.item.ItemStack;

import java.util.List;

public class BasicBundleComponent extends CustomBundleComponent {

	public BasicBundleComponent(int capacity, List<ItemStack> contents, int occupancy) {
		super(capacity, contents, occupancy);
	}

	@Override
	public int getItemOccupancy(ItemStack stack) {
		return (64 / stack.getMaxCount()) * stack.getCount();
	}

}
