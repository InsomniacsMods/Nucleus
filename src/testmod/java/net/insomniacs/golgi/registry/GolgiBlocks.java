package net.insomniacs.golgi.registry;

import net.insomniacs.golgi.Golgi;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class GolgiBlocks {

    public static void init() {
        var test = Registry.register(
                Registries.BLOCK, Golgi.of("test"), new Block(AbstractBlock.Settings.create())
        );
        Registry.register(
                Registries.ITEM, Golgi.of("test"), new BlockItem(test, new Item.Settings())
        );
    }



}
