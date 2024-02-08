package net.insomniacs.nucleus.impl.mixins.common;

import net.insomniacs.nucleus.impl.recipes.custom.BrewingItemRecipe;
import net.insomniacs.nucleus.impl.recipes.util.ItemStackBrewingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "isValidIngredient", at = @At("RETURN"), cancellable = true)
    private static void validateItemStackRecipe(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStackBrewingRecipe recipe : BrewingItemRecipe.ITEM_STACK_RECIPES) {
            if (recipe.ingredient.test(stack)) {
                cir.setReturnValue(true); return;
            }
        }
    }

    @Inject(method = "hasRecipe", at = @At("RETURN"), cancellable = true)
    private static void hasItemStackRecipe(ItemStack base, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        Item item = base.getItem();
        for (ItemStackBrewingRecipe recipe : BrewingItemRecipe.ITEM_STACK_RECIPES) {
            if (recipe.input.isOf(item) || recipe.ingredient.test(ingredient)) {
                cir.setReturnValue(true); return;
            }
        }
    }

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private static void craftItemStackRecipe(ItemStack base, ItemStack ingredient, CallbackInfoReturnable<ItemStack> cir) {
        if (base.isEmpty()) return;

        Item item = base.getItem();
        for (ItemStackBrewingRecipe recipe : BrewingItemRecipe.ITEM_STACK_RECIPES) {
            if (recipe.input.isOf(item) && recipe.ingredient.test(ingredient)) {
                cir.setReturnValue(recipe.output); return;
            }
        }
    }

}
