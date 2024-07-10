package net.insomniacs.nucleus.datagen.api.annotations;

import net.insomniacs.nucleus.datagen.impl.annotations.ShapedRecipes;
import net.minecraft.recipe.book.RecipeCategory;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ShapedRecipes.class)
public @interface ShapedCrafting {
    String pattern() default """
            ###
            ###
            ###
            """;

    String inputs();

    RecipeCategory category() default RecipeCategory.MISC;
    String keyOverride() default "";
}
