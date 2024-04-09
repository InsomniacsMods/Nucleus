package net.insomniacs.nucleus.api.modreg;

import net.insomniacs.nucleus.api.modreg.utils.CustomRegistrySupplier;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.utils.SettingCustomRegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModRegistry {

    private static final List<ModEntry<?,?,?>> ENTRIES = new LinkedList<>();

    public static List<ModEntry<?,?,?>> getEntries() {
        return ENTRIES;
    }

    @SuppressWarnings("unchecked")
    public static <T extends ModEntry<?,?,?>> List<T> getEntries(Identifier type) {
        return ENTRIES.stream()
                .filter(entry -> entry.getType().equals(type))
                .map(entry -> (T)entry)
                .toList();
    }

    public static <T extends ModEntry<?,?,?>> List<T> getEntries(String type) {
        return getEntries(new Identifier(type));
    }

    public static void addEntry(ModEntry<?,?,?> entry) {
        ENTRIES.add(0, entry);
    }

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

    public <B, T> CustomRegistrySupplier<B, T> custom(Function<Identifier, B> builderConstructor) {
        return new CustomRegistrySupplier<>(builderConstructor, modID);
    }

    public <B, S, T> SettingCustomRegistrySupplier<B, S, T> custom(BiFunction<Identifier, Function<S, T>, B> builderConstructor) {
        return new SettingCustomRegistrySupplier<>(builderConstructor, modID);
    }

}
