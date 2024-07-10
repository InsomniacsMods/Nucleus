package net.insomniacs.nucleus.datagen.api.annotations;

import net.insomniacs.nucleus.datagen.impl.annotations.ShapelessRecipes;
import net.minecraft.recipe.book.RecipeCategory;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ShapelessRecipes.class)
public @interface ShapelessCrafting {
    String input();

    RecipeCategory category() default RecipeCategory.MISC;
    String keyOverride() default "";
}
