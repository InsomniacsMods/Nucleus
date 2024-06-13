package net.insomniacs.nucleus.datagen.api.annotations;

import net.insomniacs.nucleus.datagen.impl.annotations.ShapedRecipes;

import java.lang.annotation.Repeatable;

@Repeatable(ShapedRecipes.class)
public @interface ShapedCrafting {
    String pattern() default """
            ###
            ###
            ###
            """;

}
