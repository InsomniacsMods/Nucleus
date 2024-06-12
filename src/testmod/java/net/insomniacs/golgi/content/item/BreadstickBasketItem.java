package net.insomniacs.golgi.content.item;

import net.insomniacs.nucleus.api.items.CustomBundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BreadstickBasketItem extends CustomBundleItem {

	public BreadstickBasketItem(Settings settings) {
		super(settings, 127);
	}

	@Override
	public int getItemOccupancy(ItemStack stack) {
		return stack.getCount();
	}

	@Override
	public boolean acceptsItem(ItemStack stack) {
		return stack.isOf(Items.BREAD) || stack.isOf(Items.STICK);
	}

}
