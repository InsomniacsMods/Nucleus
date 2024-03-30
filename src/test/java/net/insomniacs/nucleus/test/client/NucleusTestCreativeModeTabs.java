package net.insomniacs.nucleus.test.client;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.test.NucleusTestItems;
import net.minecraft.item.ItemGroups;

public class NucleusTestCreativeModeTabs {

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(entries -> {
            if (!entries.shouldShowOpRestrictedItems()) return;
//            entries.add(NucleusTestItems.LOCATION_BINDING_ITEM);
            entries.add(NucleusTestItems.MOB_SPAWNING_ITEM);
        });
    }

}
