package net.insomniacs.nucleus.api.modreg.entries;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.DefaultedModEntry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

public class BlockEntry extends DefaultedModEntry<Block, BlockEntry.Builder> {

    @Override
    public @NotNull Identifier getType() {
        return Nucleus.id("block");
    }

    @Nullable public ItemEntry.Builder item;

    public BlockEntry(Builder settings) {
        super(settings);
//        this.item = settings.itemBuilder.apply(this.value());
    }

    public static class Builder extends DefaultedModEntry.Builder<Builder, BlockEntry, Block.Settings, Block> {

        public Builder(Identifier id, Function<Block.Settings, Block> constructor) {
            super(id, constructor, AbstractBlock.Settings.create(), Registries.BLOCK);
        }

        @Override
        protected BlockEntry createEntry() {
            return new BlockEntry(this);
        }

        // Light

        public Builder luminance(ToIntFunction<BlockState> valueFunction) {
            return modifySettings(settings -> settings.luminance(valueFunction));
        }

        public Builder luminance(int value) {
            return luminance(n -> value);
        }

        // Item

        private ItemEntry.Builder itemBuilder;

        public Builder defaultItem(Identifier id, BiFunction<Block, Item.Settings, BlockItem> constructor) {
            this.itemBuilder = new ItemEntry.Builder(id, settings -> constructor.apply(null, settings));
            return this;
        };

        public Builder defaultItem(String name, BiFunction<Block, Item.Settings, BlockItem> constructor) {
            return defaultItem(this.id.withPath(name), constructor);
        };

        public Builder defaultItem(BiFunction<Block, Item.Settings, BlockItem> constructor) {
            return defaultItem(this.id, constructor);
        };

        public Builder defaultItem() {
            return defaultItem(this.id, BlockItem::new);
        };


        public Builder modifyItem(UnaryOperator<ItemEntry.Builder> modifier) {
            this.itemBuilder = modifier.apply(itemBuilder);
            return this;
        }

    }

}
