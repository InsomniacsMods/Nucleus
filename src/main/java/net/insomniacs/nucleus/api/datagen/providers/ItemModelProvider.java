package net.insomniacs.nucleus.api.datagen.providers;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class ItemModelProvider extends BaseModelProvider<ItemModelGenerator> {

    public void add(Item... items) {
        addAll(items, (generator, item) -> generator.register(item, Models.GENERATED));
    }

    public void addTool(Item... items) {
        addAll(items, (generator, item) -> generator.register(item, Models.HANDHELD));
    }

}