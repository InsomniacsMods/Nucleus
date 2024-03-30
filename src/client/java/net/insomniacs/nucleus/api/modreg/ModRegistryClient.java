package net.insomniacs.nucleus.api.modreg;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.api.modreg.provider.item.ItemEntry;
import net.insomniacs.nucleus.api.modreg.provider.item.ItemGroupEntry;
import net.minecraft.item.ItemGroups;

import java.util.List;

public class ModRegistryClient {

    public static void init() {

        List<ItemGroupEntry> entries = ItemEntry.CREATIVE_TABS;

        for (ItemGroupEntry entry : entries) {
            
        }

//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
//            content.add(CUSTOM_ITEM);
//        });
    }

}
