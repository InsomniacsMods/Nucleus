package net.insomniacs.golgi;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class GolgiItems {

    public static void init() {}

    public static final Item REDSTONE_TRACKER = register(
            "redstone_tracker",
            new LocationBindingItem(new Item.Settings())
    );

    public static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Golgi.of(id), item);
    }

}
