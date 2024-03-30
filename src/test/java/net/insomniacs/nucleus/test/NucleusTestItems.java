package net.insomniacs.nucleus.test;

import net.insomniacs.nucleus.impl.items.LocationBindingItem;
import net.insomniacs.nucleus.impl.items.MobSpawningItem;
import net.insomniacs.nucleus.impl.items.SignFontChangingItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static net.insomniacs.nucleus.test.NucleusTest.REGISTRY;

public class NucleusTestItems {

    public static final Item BASIC = REGISTRY.item("basic_item", Item::new).register().value();

    public static final Item ILLAGER_RUNE = REGISTRY.item("illager_rune",
            settings -> new SignFontChangingItem(settings, new Identifier("illageralt"), SoundEvents.ITEM_DYE_USE)
    )
            .unstackable()
            .register().value();

    public static final Item LOCATION_BINDING_ITEM = REGISTRY.item("location_binding_item", LocationBindingItem::new)
            .unstackable()
            .creativeTab(ItemGroups.OPERATOR)
            .register().value();

    public static void init() {}

}
