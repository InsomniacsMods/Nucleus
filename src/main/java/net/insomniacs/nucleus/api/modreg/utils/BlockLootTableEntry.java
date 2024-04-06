package net.insomniacs.nucleus.api.modreg.utils;

import net.minecraft.item.ItemConvertible;

public class BlockLootTableEntry {

    public ItemConvertible item;
    public boolean generateSelf;

    public BlockLootTableEntry(ItemConvertible item) {
        this.item = item;
    }

    public BlockLootTableEntry(Boolean self) {
        this.generateSelf = self;
    }

}
