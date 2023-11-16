package net.insomniacs.nucleus.impl.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record Location(BlockPos pos, RegistryKey<World> worldRegistryKey) {

    public static final Codec<Location> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("position").forGetter(Location::pos),
            World.CODEC.fieldOf("world").forGetter(Location::worldRegistryKey)
    ).apply(instance, Location::new));

}