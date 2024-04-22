package net.insomniacs.nucleus.impl;

import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NucleusItemPredicates {

    private static final Map<Class<? extends Item>, Map<String, ClampedModelPredicateProvider>> ITEM_PREDICATES = new HashMap<>();

    public static void addProvider(Class<? extends Item> itemClass, String path, ClampedModelPredicateProvider provider) {
        if (!ITEM_PREDICATES.containsKey(itemClass)) ITEM_PREDICATES.put(itemClass, new HashMap<>());

        Map<String, ClampedModelPredicateProvider> predicates = ITEM_PREDICATES.get(itemClass);
        predicates.put(path, provider);
    }

    public static void register() {
        Registries.ITEM.forEach(NucleusItemPredicates::addProvider);
        ITEM_PREDICATES.clear();
    }

    public static void addProvider(Item item) {

        ITEM_PREDICATES.keySet().stream()
                .filter(itemClass -> itemClass.isAssignableFrom(item.getClass()))
                .map(ITEM_PREDICATES::get)
                .forEach(map -> map.forEach(
                        (path, provider) -> ModelPredicateProviderRegistry.register(item, new Identifier(path), provider)
                ));

    }

}
