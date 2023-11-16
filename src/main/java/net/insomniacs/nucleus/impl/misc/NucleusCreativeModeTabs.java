package net.insomniacs.nucleus.impl.misc;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.impl.items.NucleusItems;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class NucleusCreativeModeTabs {

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(entries -> {
            entries.add(NucleusItems.LOCATION_BINDING_ITEM);
            entries.add(NucleusItems.MOB_SPAWNING_ITEM);
        });
    }

}
