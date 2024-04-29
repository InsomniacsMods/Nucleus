package net.insomniacs.nucleus.impl.mixins;

import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Redirect(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean dropEmptyOrSoulbound(ItemStack stack) {
        boolean isEmpty = stack.isEmpty();
        boolean isSoulbound = stack.contains(NucleusComponents.SOULBOUND);
        return !isEmpty && !isSoulbound;
    }

}