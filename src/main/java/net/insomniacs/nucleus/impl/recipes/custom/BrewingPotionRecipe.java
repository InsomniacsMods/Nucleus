package net.insomniacs.nucleus.impl.recipes.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.insomniacs.nucleus.api.recipe.CodecRecipeSerializer;
import net.insomniacs.nucleus.impl.recipes.NucleusRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public record BrewingPotionRecipe(Potion base, Ingredient ingredient, Potion result) implements Recipe<RecipeInput> {

	public static final Codec<Potion> POTION_CODEC = Registries.POTION.getCodec();

	public static final MapCodec<BrewingPotionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			POTION_CODEC.fieldOf("base").forGetter(BrewingPotionRecipe::base),
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(BrewingPotionRecipe::ingredient),
			POTION_CODEC.fieldOf("result").forGetter(BrewingPotionRecipe::result)
	).apply(instance, BrewingPotionRecipe::register));

	public static final RecipeSerializer<BrewingPotionRecipe> SERIALIZER = new CodecRecipeSerializer<>(CODEC);

	@Override
	public RecipeType<?> getType() {
		return NucleusRecipes.BREWING_POTION;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static BrewingPotionRecipe register(
			Potion base,
			Ingredient ingredient,
			Potion result
	) {
		BrewingPotionRecipe recipe = new BrewingPotionRecipe(base, ingredient, result);
		FabricBrewingRecipeRegistryBuilder.BUILD.register(recipe::register);
		return recipe;
	}

	public void register(FabricBrewingRecipeRegistryBuilder builder) {
		builder.registerPotionRecipe(
				Registries.POTION.getEntry(base),
				ingredient,
				Registries.POTION.getEntry(result)
		);
	}


	@Override
	public boolean matches(RecipeInput input, World world) {
		return false;
	}

	@Override
	public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		return null;
	}

	@Override
	public boolean fits(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return null;
	}

}
