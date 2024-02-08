package net.insomniacs.nucleus.impl.recipes.custom;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public abstract class BrewingRecipe<T> extends BrewingRecipeRegistry.Recipe<T> implements Recipe<Inventory> {

    public T getBase() {
        return input;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public T getResult() {
        return output;
    }

    public BrewingRecipe(T input, Ingredient ingredient, T output) {
        super(input, ingredient, output);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return true;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return null;
    }

}
