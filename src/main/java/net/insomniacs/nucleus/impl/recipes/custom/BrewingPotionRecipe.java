package net.insomniacs.nucleus.impl.recipes.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BrewingPotionRecipe extends BrewingRecipe<Potion> {

    public static Potion getPotion(Identifier id) {
        return Registries.POTION.get(id);
    }

    public static Identifier getID(Potion potion) {
        return Registries.POTION.getId(potion);
    }

    public BrewingPotionRecipe(Potion input, Ingredient ingredient, Potion output) {
        super(input, ingredient, output);
        FabricBrewingRecipeRegistry.registerPotionRecipe(input, ingredient, output);
    }


    public static final Codec<Potion> POTION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("potion").forGetter(BrewingPotionRecipe::getID)
    ).apply(instance, BrewingPotionRecipe::getPotion));

    public static final Codec<BrewingPotionRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            POTION_CODEC.fieldOf("base").forGetter(BrewingPotionRecipe::getBase),
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(BrewingPotionRecipe::getIngredient),
            POTION_CODEC.fieldOf("result").forGetter(BrewingPotionRecipe::getResult)
    ).apply(instance, BrewingPotionRecipe::new));

    public BrewingPotionRecipe(
            PacketByteBuf buf
    ) {
        this(
                getPotion(buf.readIdentifier()),
                Ingredient.fromPacket(buf),
                getPotion(buf.readIdentifier())
        );
    }

    public void writePacket(PacketByteBuf buf) {
        buf.writeIdentifier(getID(input));
        ingredient.write(buf);
        buf.writeIdentifier(getID(output));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BrewingPotionRecipe.Serializer.INSTANCE;
    }


    public static class Serializer implements RecipeSerializer<BrewingPotionRecipe> {

        public static final BrewingPotionRecipe.Serializer INSTANCE = new BrewingPotionRecipe.Serializer();

        @Override
        public Codec<BrewingPotionRecipe> codec() {
            return CODEC;
        }

        @Override
        public BrewingPotionRecipe read(PacketByteBuf buf) {
            return new BrewingPotionRecipe(buf);
        }

        @Override
        public void write(PacketByteBuf buf, BrewingPotionRecipe recipe) {
            recipe.writePacket(buf);
        }

    }

    @Override
    public RecipeType<?> getType() {
        return BrewingPotionRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<BrewingPotionRecipe> {
        public static final BrewingPotionRecipe.Type INSTANCE = new BrewingPotionRecipe.Type();
    }

}
