package net.insomniacs.nucleus.impl.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class NucleusItems {

    public static final Item LOCATION_BINDING_ITEM = registerItem("location_binding_item",
            new LocationBindingItem(new FabricItemSettings().maxCount(1)));
    public static final Item MOB_SPAWNING_ITEM = registerItem("mob_spawning_item",
            new MobSpawningItem(new FabricItemSettings().maxCount(1), EntityType.ZOMBIE));

    public static void init() {}

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Nucleus.id(name), item);
    }

}
