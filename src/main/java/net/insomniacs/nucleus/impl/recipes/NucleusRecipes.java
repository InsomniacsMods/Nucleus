package net.insomniacs.nucleus.impl.recipes;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.impl.recipes.custom.BrewingItemRecipe;
import net.insomniacs.nucleus.impl.recipes.custom.BrewingPotionRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class NucleusRecipes {

    public static final RecipeSerializer<BrewingItemRecipe> BREWING_ITEM_SERIALIZER = register(Nucleus.id("assembly"), BrewingItemRecipe.Serializer.INSTANCE);
//    public static final RecipeSerializer<BrewingPotionRecipe> BREWING_POTION_SERIALIZER = register(Nucleus.id("alloy_forging"), BrewingPotionRecipe.Serializer.INSTANCE);

    public static final RecipeType<BrewingItemRecipe> BREWING_ITEM = register(Nucleus.id("brewing_item"));
//    public static final RecipeType<BrewingPotionRecipe> BREWING_POTION = register(Nucleus.id("brewing_potion"));

    public static void init() {

    }

    public static <T extends Recipe<?>> RecipeSerializer<T> register(Identifier id, RecipeSerializer<T> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
    }

    public static <T extends Recipe<?>> RecipeType<T> register(Identifier id) {
        return Registry.register(Registries.RECIPE_TYPE, id, new GenericRecipeType<>(id));
    }

    public record GenericRecipeType<T extends Recipe<?>> (Identifier id) implements RecipeType<T> {
        public String toString() {
            return id.toString();
        }
    }

}
