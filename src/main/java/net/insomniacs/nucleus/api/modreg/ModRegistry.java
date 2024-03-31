package net.insomniacs.nucleus.api.modreg;

import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModRegistry {

    public static final List<DefaultedModEntry<?, ?>> ENTRIES = new ArrayList<>();

    private final String modID;

    public ModRegistry(String modID) {
        this.modID = modID;
    }

    public ItemEntry.Builder item(String id, Function<Item.Settings, Item> constructor) {
        return new ItemEntry.Builder(new Identifier(modID, id), constructor);
    }

    public BlockEntry.Builder block(String id, Function<AbstractBlock.Settings, Block> constructor) {
        return new BlockEntry.Builder(new Identifier(modID, id), constructor);
    }

    public <B, S, T> CustomRegistrySupplier<B, S, T> custom(BiFunction<Identifier, Function<S, T>, B> builderConstructor) {
        return new CustomRegistrySupplier<>(builderConstructor, modID);
    }

}
