package net.insomniacs.nucleus.impl.recipes.util;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;

public class ItemStackBrewingRecipe extends BrewingRecipeRegistry.Recipe<ItemStack> {

    public final Ingredient base;

    public ItemStackBrewingRecipe(Ingredient base, Ingredient ingredient, ItemStack output) {
        super(null, ingredient, output);
        this.base = base;
    }

}
