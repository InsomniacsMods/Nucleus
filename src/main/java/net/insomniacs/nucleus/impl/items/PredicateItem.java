package net.insomniacs.nucleus.impl.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class PredicateItem extends Item implements HasPredicates {

    public PredicateItem(Settings settings) {
        super(settings);
    }

    public abstract float registerPredicates(ItemStack stack, World world, LivingEntity entity, int i);

}
