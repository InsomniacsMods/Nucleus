package net.insomniacs.nucleus.api.modreg.provider.item;

import net.insomniacs.nucleus.api.modreg.provider.Entry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemEntry extends Entry<Item, ItemBuilder> {

    public static final List<ItemGroupEntry> CREATIVE_TABS = new ArrayList<>();

    public ItemEntry(Identifier id, Item value, ItemBuilder settings) {
        super(id, value, settings);
    }

}
