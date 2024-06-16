package net.insomniacs.golgi.content.item;

import net.insomniacs.nucleus.api.items.NucleusBundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BreadstickBasketItem extends NucleusBundleItem {

	public BreadstickBasketItem(Settings settings, int defaultCapacity) {
		super(settings, defaultCapacity);
	}

//	@Override
//	public boolean acceptsItem(ItemStack stack) {
//		return stack.isOf(Items.BREAD) || stack.isOf(Items.STICK);
//	}

}
