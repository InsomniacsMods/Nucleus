package net.insomniacs.nucleus.impl.mixins;

import net.insomniacs.nucleus.impl.misc.NucleusTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Mutable @Final @Shadow private final List<DefaultedList<ItemStack>> combinedInventory;
    @Mutable @Final @Shadow public final PlayerEntity player;

    public PlayerInventoryMixin(List<DefaultedList<ItemStack>> combinedInventory, PlayerEntity player) {
        this.combinedInventory = combinedInventory;
        this.player = player;
    }

    /**
     * @author Soumeh
     * @reason Replace with implementation which keeps soulbound items.
     */
    @Overwrite
    public void dropAll() {
        for (List<ItemStack> list : this.combinedInventory) {
            for(int i = 0; i < list.size(); ++i) {
                ItemStack stack = list.get(i);
                if (stack.isEmpty()) continue;
                if (stack.isSoulbound()) continue;

                this.player.dropItem(stack, true, false);
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

}