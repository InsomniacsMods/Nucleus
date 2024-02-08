package net.insomniacs.nucleus.test;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.impl.items.LocationBindingItem;
import net.insomniacs.nucleus.impl.items.MobSpawningItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class NucleusTestItems {

    public static final Item LOCATION_BINDING_ITEM = registerItem("location_binding_item",
            new LocationBindingItem(new FabricItemSettings().maxCount(1)));
    public static final Item MOB_SPAWNING_ITEM = registerItem("mob_spawning_item",
            new MobSpawningItem(new FabricItemSettings().maxCount(1).food(new FoodComponent.Builder().snack().build()), EntityType.ZOMBIE));

    public static void init() {}

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Nucleus.id(name), item);
    }

}
