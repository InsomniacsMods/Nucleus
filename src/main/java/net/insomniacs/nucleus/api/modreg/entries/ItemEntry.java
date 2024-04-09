package net.insomniacs.nucleus.api.modreg.entries;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.SettingsModEntry;
import net.insomniacs.nucleus.api.modreg.utils.ItemGroupEntry;
import net.insomniacs.nucleus.api.modreg.utils.ItemModelEntry;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class ItemEntry extends SettingsModEntry<ItemEntry, ItemEntry.Builder, Item.Settings, Item> {

    @Override
    public Registry<Item> getRegistry() {
        return Registries.ITEM;
    }

    public ItemEntry(Builder settings) {
        super(settings);
    }

    public List<ItemGroupEntry> getCreativeTabs() {
        return this.settings.creativeTabs;
    }

    public ItemModelEntry getModel() {
        return this.settings.model;
    }

    public List<CraftingRecipeJsonBuilder> getRecipes() {
        return this.settings.recipes;
    }

    public static class Builder extends SettingsModEntry.EntryBuilder<Builder, ItemEntry, Item.Settings, Item> {

        public Builder(Identifier id, Function<Item.Settings, Item> constructor) {
            super(id, constructor);
        }

        public Builder(Identifier id) {
            super(id);
        }

        @Override
        protected Item.Settings generateSettings() {
            return new FabricItemSettings();
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

        // Recipes

        private final List<CraftingRecipeJsonBuilder> recipes = new ArrayList<>();

        public Builder addRecipe(CraftingRecipeJsonBuilder recipe) {
            this.recipes.add(recipe);
            return this;
        }

        public Builder shapedRecipe(RecipeCategory category, List<String> pattern, HashMap<Character, ItemConvertible> ingredients) {
            var recipe = ShapedRecipeJsonBuilder.create(category, null);

            pattern.forEach(recipe::pattern);
            ingredients.forEach(recipe::input);
            ingredients.values().forEach(ingredient ->
                    recipe.criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
            );

            return addRecipe(recipe);
        }

        public Builder shapedRecipe(List<String> pattern, HashMap<Character, ItemConvertible> ingredients) {
            return shapedRecipe(RecipeCategory.MISC, pattern, ingredients);
        }

        public Builder shapelessRecipe(RecipeCategory category, List<ItemConvertible> ingredients) {
            var recipe = ShapelessRecipeJsonBuilder.create(category, null);

            ingredients.forEach(recipe::input);
            ingredients.forEach(ingredient ->
                    recipe.criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
            );

            return addRecipe(recipe);
        }

        public Builder shapelessRecipe(List<ItemConvertible> ingredients) {
            return shapelessRecipe(RecipeCategory.MISC, ingredients);
        }

        public Builder shapelessRecipe(ItemConvertible... ingredients) {
            return shapelessRecipe(List.of(ingredients));
        }

    }

}
