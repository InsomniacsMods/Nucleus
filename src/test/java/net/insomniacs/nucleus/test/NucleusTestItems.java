package net.insomniacs.nucleus.test;

import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.impl.items.LocationBindingItem;
import net.insomniacs.nucleus.impl.items.SignFontChangingItem;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static net.insomniacs.nucleus.test.NucleusTest.REGISTRY;

public class NucleusTestItems {

    public static final ItemEntry BASIC = REGISTRY.item("basic_item", Item::new)
            .translate("Basic Item")
            .defaultModel()
            .register();

    public static final ItemEntry ILLAGER_RUNE = REGISTRY.item("illager_rune",
            settings -> new SignFontChangingItem(settings, new Identifier("illageralt"), SoundEvents.ITEM_DYE_USE)
    )
            .translate()
            .defaultModel()
            .unstackable()
            .register();

    public static final ItemEntry LOCATION_BINDING_ITEM = REGISTRY.item("location_binding_item", LocationBindingItem::new)
            .translate()
            .handheldModel()
            .unstackable()
            .creativeTab(ItemGroups.OPERATOR)
            .register();

    public static final ItemEntry BASIC_PICKAXE = REGISTRY.item("basic_pickaxe",
            settings -> new PickaxeItem(ToolMaterials.STONE, 1, 1, settings)
    )
            .translate()
            .pickaxe()
            .creativeTab(ItemGroups.TOOLS, Items.DIAMOND_PICKAXE)
            .register();

    public static void init() {}

}
