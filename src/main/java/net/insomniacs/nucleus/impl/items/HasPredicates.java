package net.insomniacs.nucleus.impl.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface HasPredicates {

    List<LocationBindingItem> ITEMS = new ArrayList<>();

    float registerPredicates(ItemStack stack, World world, LivingEntity entity, int i);

}
