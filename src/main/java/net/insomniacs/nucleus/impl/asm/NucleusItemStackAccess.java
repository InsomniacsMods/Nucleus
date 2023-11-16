package net.insomniacs.nucleus.impl.asm;

import net.insomniacs.nucleus.impl.misc.NucleusTags;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public interface NucleusItemStackAccess {

    @SuppressWarnings({"RedundantIfStatement"})
    default boolean isSoulbound() {
        ItemStack stack = (ItemStack)this;
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("soulbound") && nbt.getBoolean("soulbound")) return true;
        else if (stack.isIn(NucleusTags.SOULBOUND_ITEMS)) return true;
        return false;
    }

}
