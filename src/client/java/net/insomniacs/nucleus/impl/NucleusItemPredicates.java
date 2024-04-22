package net.insomniacs.nucleus.impl;

import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NucleusItemPredicates {

    public static Map<Class<? extends Item>, Map<String, ClampedModelPredicateProvider>> ITEM_PREDICATES = new HashMap<>();

    public static void registerPredicates() {

        registerPredicateProvider(
            LocationBindingItem.class,
            "has_location",
            (ItemStack stack, ClientWorld world, LivingEntity entity, int seed) -> LocationBindingItem.hasLocationPredicate(stack)
        );

//        registerPredicateProvider(
//                CustomBundleItem.class,
//                "filled",
//                (ItemStack stack, ClientWorld world, LivingEntity entity, int seed) -> CustomBundleItem.getAmountFilled(stack)
//        );

        Registries.ITEM.forEach(NucleusItemPredicates::registerPredicateProvider);
        ITEM_PREDICATES.clear();

    }

    public static void registerPredicateProvider(Class<? extends Item> itemClass, String path, ClampedModelPredicateProvider provider) {
        if (!ITEM_PREDICATES.containsKey(itemClass)) ITEM_PREDICATES.put(itemClass, new HashMap<>());

        Map<String, ClampedModelPredicateProvider> predicates = ITEM_PREDICATES.get(itemClass);
        predicates.put(path, provider);
    }

    public static void registerPredicateProvider(Item item) {
        for (Class<? extends Item> newItem : ITEM_PREDICATES.keySet()) {
            if (!newItem.isAssignableFrom(item.getClass())) continue;

            Map<String, ClampedModelPredicateProvider> predicates = ITEM_PREDICATES.get(newItem);
            for (String path : predicates.keySet()) {
                ClampedModelPredicateProvider provider = predicates.get(path);
                ModelPredicateProviderRegistry.register(item, new Identifier(path), provider);
            }
        }
    }

}
