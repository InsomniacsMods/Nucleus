package net.insomniacs.golgi;

import net.insomniacs.golgi.test.BrewingRecipe;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GolgiRecipeTypes {

	public static final RecipeType<BrewingRecipe> BREWING = register("brewing");


	public static void init() {}


	static <T extends Recipe<?>> RecipeType<T> register(String path) {
		Identifier id = Nucleus.of(path);
		var type = new RecipeType<T>() {
			public String toString() { return id.toString(); }
		};
		return Registry.register(Registries.RECIPE_TYPE, id, type);
	}

}
