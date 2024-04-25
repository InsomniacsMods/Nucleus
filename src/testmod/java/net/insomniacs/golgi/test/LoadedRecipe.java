package net.insomniacs.golgi.test;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public interface LoadedRecipe extends GenericRecipe {

	@Override
	default boolean matches(Inventory inventory, World world) {
		return true;
	}

	@Override
	default ItemStack craft(Inventory inventory, RegistryWrapper.WrapperLookup lookup) {
		return null;
	}

	@Override
	default ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return null;
	}

}
