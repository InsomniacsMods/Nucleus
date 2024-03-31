package net.insomniacs.nucleus.api.modreg.entries;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TestEntry extends ModEntry<Item, TestEntry.Builder> {

    @Override
    public Identifier getType() {
        return Nucleus.id("test");
    }

    public static class Constructor implements ModEntry.Constructor<Item> {

        @Override
        public Item construct() {
            return null;
        }

    }

    public static class Builder extends ModEntry.Builder<Builder, TestEntry, TestEntry.Constructor, Item> {

        public Builder(Identifier id, Constructor constructor, Registry<Item> registry) {
            super(id, constructor, registry);
        }

        @Override
        protected TestEntry createEntry() {
            return null;
        }

    }

}
