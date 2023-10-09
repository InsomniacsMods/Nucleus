package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;

/**
 * An interface to represent data components.
 * Inspiration was taken from Cardinal components, but back-end is entirely me.
 * @author dragoncommands
 */
public interface DataComponent {

    /**
     * Will read data from inputted NBT data, this is where you perform operations to translate data to variables.
     * @param tag: the tag to read data from.
     */
    void readFromNBT(NbtCompound tag);

    /**
     * Will write data to tag. This is where write operations for data are stored.
     * @param tag: the tag to write data to
     */
    void writeToNBT(NbtCompound tag);

    Codec<?> getCodec();
}
