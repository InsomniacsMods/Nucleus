package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record Location(BlockPos pos, RegistryKey<World> worldKey) {

    public static final Location ZERO = new Location(
            new BlockPos(0, 0, 0),
            null
    );

    public boolean isZero() {
        return this.equals(ZERO);
    }

    public static final Codec<Location> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("position").forGetter(Location::pos),
            World.CODEC.fieldOf("world").forGetter(Location::worldKey)
    ).apply(instance, Location::new));

    public String dimensionId() {
        return this.worldKey.getValue().toTranslationKey("dimension");
    }

    public Text dimensionName() {
        return Text.translatable(dimensionId());
    }

    public int x() { return pos.getX(); }
    public int y() { return pos.getY(); }
    public int z() { return pos.getZ(); }

}