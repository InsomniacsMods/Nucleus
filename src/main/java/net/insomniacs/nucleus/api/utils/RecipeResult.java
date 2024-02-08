package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;

import java.util.Optional;

public class RecipeResult {

    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Registries.ITEM.createEntryCodec().fieldOf("item").forGetter(ItemStack::getRegistryEntry),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter((stack) -> Optional.ofNullable(stack.getNbt()))
    ).apply(instance, ItemStack::new));

}
