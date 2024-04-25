package net.insomniacs.nucleus.api.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public interface GenericRecipe extends Recipe<Inventory> {

	@Override
	default boolean matches(Inventory inventory, World world) {
		return false;
	}

	@Override
	default ItemStack craft(Inventory inventory, RegistryWrapper.WrapperLookup lookup) {
		return null;
	}

	@Override
	default boolean fits(int width, int height) {
		return false;
	}

	@Override
	default ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return null;
	}

}
