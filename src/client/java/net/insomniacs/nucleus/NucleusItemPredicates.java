package net.insomniacs.nucleus;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class NucleusItemPredicates {

    public static void registerPredicates() {
//        CustomBundleItem.registerPredicates();
//        CorpsefluteItem.registerPredicates();
//        AnimaShacklesItem.registerPredicates();
    }

    @SuppressWarnings("deprecation")
    public static void registerPredicate(Item item, String location, ClampedModelPredicateProvider provider) {
        FabricModelPredicateProviderRegistry.register(item, new Identifier(location), provider);
    }

}
