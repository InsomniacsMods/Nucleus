package net.insomniacs.nucleus.datagen.api.annotations;

import net.insomniacs.nucleus.datagen.impl.annotations.ShapedRecipes;
import net.minecraft.recipe.book.RecipeCategory;

import java.lang.annotation.Repeatable;

@Repeatable(ShapedRecipes.class)
public @interface ShapedCrafting {
    String pattern() default """
            ###
            ###
            ###
            """;

    String inputs();

    RecipeCategory category() default RecipeCategory.MISC;
}
