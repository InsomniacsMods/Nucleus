package net.insomniacs.golgi.test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.insomniacs.golgi.GolgiRecipeTypes;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record BrewingRecipe(Potion base, Ingredient ingredient, Potion result) implements LoadedRecipe {

	public static BrewingRecipe register(
			Potion base,
			Ingredient ingredient,
			Potion result
	) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		BrewingRecipe recipe = new BrewingRecipe(base, ingredient, result);
		FabricBrewingRecipeRegistryBuilder.BUILD.register(recipe::register);
		return recipe;
	}

	public void register(FabricBrewingRecipeRegistryBuilder builder) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(base);
		System.out.println(ingredient);
		System.out.println(result);
		builder.registerPotionRecipe(
				Registries.POTION.getEntry(base),
				ingredient,
				Registries.POTION.getEntry(result)
		);
	}

	@Override
	public RecipeType<?> getType() {
		return GolgiRecipeTypes.BREWING;
	}

	public static final Codec<Potion> POTION_CODEC = Identifier.CODEC.xmap(
			Registries.POTION::get, Registries.POTION::getId
	);

	public static final MapCodec<BrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			POTION_CODEC.fieldOf("base").forGetter(BrewingRecipe::base),
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(BrewingRecipe::ingredient),
			POTION_CODEC.fieldOf("result").forGetter(BrewingRecipe::result)
	).apply(instance, BrewingRecipe::register));

	@Override
	public MapCodec<BrewingRecipe> getCodec() {
		return CODEC;
	}

}
