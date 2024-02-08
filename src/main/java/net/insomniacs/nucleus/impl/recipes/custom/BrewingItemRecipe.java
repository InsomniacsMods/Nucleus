package net.insomniacs.nucleus.impl.recipes.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.insomniacs.nucleus.api.utils.RecipeResult;
import net.insomniacs.nucleus.impl.recipes.util.ItemStackBrewingRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class BrewingItemRecipe implements BrewingRecipe {

    public static final List<ItemStackBrewingRecipe> ITEM_STACK_RECIPES = Lists.newArrayList();

    public final Ingredient base;
    public final Ingredient ingredient;
    public final ItemStack result;

    public Ingredient getBase() {
        return base;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public ItemStack getResult() {
        return result;
    }

    public BrewingItemRecipe(
            Ingredient base,
            Ingredient ingredient,
            ItemStack result
    ) {
        this.base = base;
        this.ingredient = ingredient;
        this.result = result;

        ITEM_STACK_RECIPES.add(new ItemStackBrewingRecipe(base, ingredient, result));
    }


    public static final Codec<BrewingItemRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter(BrewingItemRecipe::getBase),
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(BrewingItemRecipe::getIngredient),
            RecipeResult.CODEC.fieldOf("result").forGetter(BrewingItemRecipe::getResult)
    ).apply(instance, BrewingItemRecipe::new));

    public BrewingItemRecipe(
            PacketByteBuf buf
    ) {
        this(
                Ingredient.fromPacket(buf),
                Ingredient.fromPacket(buf),
                buf.readItemStack()
        );
    }

    public void writePacket(PacketByteBuf buf) {
        base.write(buf);
        ingredient.write(buf);
        buf.writeItemStack(result);
    }


    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return null;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return BrewingItemRecipe.Serializer.INSTANCE;
    }


    public static class Serializer implements RecipeSerializer<BrewingItemRecipe> {

        public static final BrewingItemRecipe.Serializer INSTANCE = new BrewingItemRecipe.Serializer();

        @Override
        public Codec<BrewingItemRecipe> codec() {
            return CODEC;
        }

        @Override
        public BrewingItemRecipe read(PacketByteBuf buf) {
            return new BrewingItemRecipe(buf);
        }

        @Override
        public void write(PacketByteBuf buf, BrewingItemRecipe recipe) {
            recipe.writePacket(buf);
        }

    }


    @Override
    public RecipeType<?> getType() {
        return BrewingItemRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<BrewingItemRecipe> {
        public static final BrewingItemRecipe.Type INSTANCE = new BrewingItemRecipe.Type();
    }
    
}
