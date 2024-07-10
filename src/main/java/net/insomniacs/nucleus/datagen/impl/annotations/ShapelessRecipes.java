package net.insomniacs.nucleus.datagen.impl.annotations;

import net.insomniacs.nucleus.datagen.api.annotations.ShapelessCrafting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ShapelessRecipes {
    ShapelessCrafting[] value();
}
