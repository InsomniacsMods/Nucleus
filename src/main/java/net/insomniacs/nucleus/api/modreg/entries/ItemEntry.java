package net.insomniacs.nucleus.api.modreg.entries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ItemGroupEntry;
import net.insomniacs.nucleus.api.modreg.DefaultedModEntry;
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

public class ItemEntry extends DefaultedModEntry<Item, ItemEntry.Builder> {

    @Override
    public Identifier getType() {
        return Nucleus.id("item");
    }

    public final List<ItemGroupEntry> creativeTabs;
    public final boolean generateModel;
    public final Model parentModel;

    public ItemEntry(Builder settings) {
        super(settings);
        this.creativeTabs = settings.creativeTabs;
        this.generateModel = settings.generateModel;
        this.parentModel = settings.parentModel;
    }

    public static class Builder extends DefaultedModEntry.Builder<Builder, ItemEntry, Item.Settings, Item> {

        public Builder(Identifier id, Function<Item.Settings, Item> constructor) {
            super(id, constructor, new FabricItemSettings(), Registries.ITEM);
        }

        @Override
        protected ItemEntry createEntry() {
            return new ItemEntry(this);
        }

        // Model

        private boolean generateModel = false;
        private Model parentModel = Models.GENERATED;

        public Builder model(Model model) {
            this.generateModel = true;
            this.parentModel = model;
            return this;
        }

        public Builder defaultModel() {
            return model(Models.GENERATED);
        }

        public Builder handheldModel() {
            return model(Models.HANDHELD);
        }

        // Creative Tabs

        private final List<ItemGroupEntry> creativeTabs = new ArrayList<>();

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
