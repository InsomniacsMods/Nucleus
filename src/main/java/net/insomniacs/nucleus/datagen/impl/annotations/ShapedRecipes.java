package net.insomniacs.nucleus.datagen.impl.annotations;

import net.insomniacs.nucleus.datagen.api.annotations.ShapedCrafting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ShapedRecipes {
    ShapedCrafting[] value();
}
