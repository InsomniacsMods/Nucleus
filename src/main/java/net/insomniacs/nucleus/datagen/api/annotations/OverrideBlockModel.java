package net.insomniacs.nucleus.datagen.api.annotations;

import net.minecraft.block.Block;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OverrideBlockModel {
    Class<? extends Block> block();
}
