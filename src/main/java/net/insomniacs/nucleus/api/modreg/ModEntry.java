package net.insomniacs.nucleus.api.modreg;

import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ModEntry<Type, Builder extends ModEntry.Builder<Builder, ?, ?, Type>> {

    public abstract Identifier getType();

    private final Identifier id;
    private final Type value;
    private Builder settings;

    public boolean translate;
    public String translatedName;
    public List<TagKey<Type>> tags;

    public ModEntry(Builder settings) {
        this.id = settings.id;
        this.value = (Type)settings.construct();
        this.settings = settings;

        this.translate = settings.translate;
        this.translatedName = settings.translatedName;

        this.tags = settings.tags;
    }

    public Type value() {
        return value;
    }

    public Identifier id() {
        return id;
    }

    public Builder settings() {
        return settings;
    }


    public interface Constructor<Type> {
        Type construct();
    }



    @SuppressWarnings("unchecked")
    public static abstract class Builder<Self extends Builder<Self, Entry, Constructor, Type>, Entry extends ModEntry<Type, Self>, Constructor extends ModEntry.Constructor<Type>, Type> {

        protected final Identifier id;
        protected final Constructor constructor;
        protected final Registry<Type> registry;

        public Builder(Identifier id, Constructor constructor, Registry<Type> registry) {
            this.id = id;
            this.constructor = constructor;
            this.registry = registry;
        }

        protected Type construct() {
            Type value = constructor.construct();
            return Registry.register(registry, id, value);
        }

        protected abstract Entry createEntry();

        public Entry register() {
            var entry = createEntry();
//            ModRegistry.ENTRIES.add(entry);
            return entry;
        }

        // Translation

        protected boolean translate = false;
        protected String translatedName = "";

        public Self translate(String name) {
            this.translate = true;
            this.translatedName = name;
            return (Self)this;
        };

        public Self translate() {
            return translate(this.toDisplayName());
        }

        // Tags

        protected List<TagKey<Type>> tags = new ArrayList<>();

        public Self tag(TagKey<Type> tag) {
            tags.add(tag);
            return (Self)this;
        }

        // Utils

        public String toDisplayName() {
            return Arrays
                    .stream(this.id.getPath().split("_"))
                    .map(StringUtils::capitalize)
                    .collect(Collectors.joining(" "));
        }

    }

}