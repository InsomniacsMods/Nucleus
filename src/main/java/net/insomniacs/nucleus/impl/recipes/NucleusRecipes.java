package net.insomniacs.nucleus.impl.recipes;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.impl.recipes.custom.BrewingPotionRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class NucleusRecipes {

	public static final RecipeType<BrewingPotionRecipe> BREWING_POTION = register("brewing_potion", BrewingPotionRecipe.SERIALIZER);


	public static void init() {}


	static <T extends Recipe<?>> RecipeType<T> register(String path, RecipeSerializer<T> serializer) {
		Identifier id = Nucleus.of(path);
		RecipeType<T> type = new RecipeType<>() {
			@Override
			public String toString() { return id.toString(); }
		};
		Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
		return Registry.register(Registries.RECIPE_TYPE, id, type);
	}

}
