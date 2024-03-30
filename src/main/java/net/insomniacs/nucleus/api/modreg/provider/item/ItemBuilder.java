package net.insomniacs.nucleus.api.modreg.provider.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.api.modreg.provider.EntryBuilder;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemBuilder extends EntryBuilder<ItemEntry, Item.Settings, Item> {

    public ItemBuilder(Identifier id, Function<Item.Settings, Item> constructor) {
        super(id, constructor, new FabricItemSettings(), Registries.ITEM);
    }

    @Override
    public ItemEntry createEntry() {
        return new ItemEntry(id, construct(), this);
    }

    @Override
    public ItemBuilder modifySettings(UnaryOperator<Item.Settings> modifier) {
        return (ItemBuilder)super.modifySettings(modifier);
    }

    public ItemBuilder creativeTab(RegistryKey<ItemGroup> tab, ItemStack after) {
        ItemEntry.CREATIVE_TABS.add(new ItemGroupEntry(tab, id, after));
        return this;
    }

    public ItemBuilder creativeTab(RegistryKey<ItemGroup> tab) {
        return creativeTab(tab, null);
    }


    public ItemBuilder maxCount(int count) {
        return modifySettings(settings -> settings.maxCount(count));
    }

    public ItemBuilder unstackable() {
        return maxCount(1);
    }


    public ItemBuilder food(FoodComponent component) {
        return modifySettings(settings -> settings.food(component));
    }

}
