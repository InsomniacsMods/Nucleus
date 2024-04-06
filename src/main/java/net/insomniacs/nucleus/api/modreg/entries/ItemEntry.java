package net.insomniacs.nucleus.api.modreg.entries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.utils.ItemGroupEntry;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.insomniacs.nucleus.api.modreg.utils.ItemModelEntry;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class ItemEntry extends ModEntry<ItemEntry, ItemEntry.Builder, Item.Settings, Item> {

    @Override
    public Identifier getType() {
        return Nucleus.id("item");
    }

    public ItemEntry(Builder settings) {
        super(settings);
    }

    public List<ItemGroupEntry> getCreativeTabs() {
        return this.settings.creativeTabs;
    }

    public ItemModelEntry getModel() {
        return this.settings.model;
    };

    public static class Builder extends EntryBuilder<Builder, ItemEntry, Item.Settings, Item> {

        public Builder(Identifier id, Function<Item.Settings, Item> constructor) {
            super(id, constructor, new FabricItemSettings(), Registries.ITEM);
        }

        public Builder(Identifier id) {
            super(id, new FabricItemSettings(), Registries.ITEM);
        }

        @Override
        protected ItemEntry createEntry() {
            return new ItemEntry(this);
        }

        // Model

        private ItemModelEntry model;

        public Builder model(Model model) {
            this.model = new ItemModelEntry(model);
            return this;
        }

        public Builder defaultModel() {
            return model(Models.GENERATED);
        }

        public Builder handheldModel() {
            return model(Models.HANDHELD);
        }

        // Creative Tabs

        protected final List<ItemGroupEntry> creativeTabs = new ArrayList<>();

        public Builder creativeTab(RegistryKey<ItemGroup> tab, ItemStack after) {
            creativeTabs.add(new ItemGroupEntry(tab, after));
            return this;
        }

        public Builder creativeTab(RegistryKey<ItemGroup> tab, Item after) {
            return creativeTab(tab, after.getDefaultStack());
        }

        public Builder creativeTab(RegistryKey<ItemGroup> tab) {
            creativeTabs.add(new ItemGroupEntry(tab, null));
            return this;
        }

        // Count

        public Builder maxCount(int count) {
            return modifySettings(settings -> settings.maxCount(count));
        }

        public Builder unstackable() {
            return maxCount(1);
        }

        // Food

        public Builder food(FoodComponent component) {
            return modifySettings(settings -> settings.food(component));
        }

        public Builder edible() {
            return food(new FoodComponent.Builder().alwaysEdible().build());
        }

        // Tags

        public Builder sword() {
            return tag(ItemTags.SWORDS);
        }
        public Builder axe() {
            return tag(ItemTags.AXES);
        }
        public Builder hoe() {
            return tag(ItemTags.HOES);
        }
        public Builder pickaxe() {
            return tag(ItemTags.PICKAXES);
        }
        public Builder shovel() {
            return tag(ItemTags.SHOVELS);
        }

    }

}
