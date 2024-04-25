package net.insomniacs.golgi.test;

import net.minecraft.inventory.Inventory;

public interface GenericRecipe extends CodecRecipe<Inventory> {

	@Override
	default boolean fits(int w, int h) { return true; }

}
