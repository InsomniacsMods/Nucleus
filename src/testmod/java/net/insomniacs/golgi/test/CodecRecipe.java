package net.insomniacs.golgi.test;

import com.mojang.serialization.MapCodec;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;

public interface CodecRecipe<C extends Inventory> extends Recipe<C> {

	MapCodec<? extends CodecRecipe<C>> getCodec();

	@SuppressWarnings("unchecked")
	default RecipeSerializer<CodecRecipe<C>> getSerializer() {
		return (CodecRecipeSerializer<CodecRecipe<C>>)new CodecRecipeSerializer<>(getCodec());
	}

}
