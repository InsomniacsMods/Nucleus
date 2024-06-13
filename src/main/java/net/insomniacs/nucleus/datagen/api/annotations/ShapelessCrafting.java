package net.insomniacs.nucleus.datagen.api.annotations;

import net.insomniacs.nucleus.datagen.impl.annotations.ShapelessRecipes;

import java.lang.annotation.Repeatable;

@Repeatable(ShapelessRecipes.class)
public @interface ShapelessCrafting {
    String entries();
}
