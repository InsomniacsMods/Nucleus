package net.insomniacs.nucleus.asm;

import net.insomniacs.nucleus.impl.misc.NucleusTags;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public interface NucleusEntityTypeAccess {

    default Identifier getId() {
        EntityType<?> type = (EntityType<?>)this;
        return Registries.ENTITY_TYPE.getId(type);
    }

}
