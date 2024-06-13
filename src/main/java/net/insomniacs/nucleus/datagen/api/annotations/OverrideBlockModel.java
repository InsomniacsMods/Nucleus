package net.insomniacs.nucleus.datagen.api.annotations;

import net.minecraft.block.Block;

public @interface OverrideBlockModel {
    Class<? extends Block> block();
}
