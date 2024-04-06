package net.insomniacs.nucleus.api.modreg;

import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public abstract class ModEntry <
        Entry extends ModEntry<Entry, Builder, Settings, Type>,
        Builder extends ModEntry.EntryBuilder<Builder, Entry, Settings, Type>,
        Settings,
        Type
> {

    public abstract Identifier getType();

    protected final Identifier id;
    protected final Type value;
    protected final Builder settings;

    public Identifier getId() {
        return id;
    }

    public Type value() {
        return value;
    }

    public Builder getSettings() {
        return settings;
    }

    public boolean translate;
    public String translatedName;
    public List<TagKey<Type>> tags;

    @SuppressWarnings("RedundantCast")
    public ModEntry(Builder settings) {
        this.id = settings.id;
        this.value = (Type)settings.construct();
        this.settings = settings;

        this.translate = settings.translate;
        this.translatedName = settings.translatedName;

        this.tags = settings.tags;
    }

//    public Type value() {
//        return value;
//    }
//
//    public Identifier id() {
//        return id;
//    }
//
//    public Builder settings() {
//        return settings;
//    }


    @SuppressWarnings("unchecked")
    public static abstract class EntryBuilder <
            Builder extends EntryBuilder<Builder, Entry, Settings, Type>,
            Entry extends ModEntry<Entry, Builder, Settings, Type>,
            Settings,
            Type
        > {

        protected final Identifier id;
        protected Settings defaultSettings;
        protected Function<Settings, Type> constructor;
        protected final Registry<Type> registry;

        public EntryBuilder(Identifier id, Function<Settings, Type> constructor, Settings defaultSettings, Registry<Type> registry) {
            this.id = id;
            this.defaultSettings = defaultSettings;
            this.constructor = constructor;
            this.registry = registry;
        }

        public EntryBuilder(Identifier id, Settings defaultSettings, Registry<Type> registry) {
            this.id = id;
            this.defaultSettings = defaultSettings;
            this.registry = registry;
        }

        public Builder setConstructor(Function<Settings, Type> constructor) {
            this.constructor = constructor;
            return (Builder)this;
        }

        public Builder modifySettings(UnaryOperator<Settings> modifier) {
            this.defaultSettings = modifier.apply(this.defaultSettings);
            return (Builder)this;
        }

        protected Type construct() {
            Type value = constructor.apply(defaultSettings);
            return Registry.register(registry, id, value);
        }

        protected abstract Entry createEntry();

        public Entry register() {
            var entry = createEntry();
            ModRegistry.addEntry(entry);
            return entry;
        }

        // Translation

        protected boolean translate = false;
        protected String translatedName = "";

        public Builder translate(String name) {
            this.translate = true;
            this.translatedName = name;
            return (Builder)this;
        };

        public Builder translate() {
            return translate(this.toDisplayName());
        }

        // Tags

        protected List<TagKey<Type>> tags = new ArrayList<>();

        public Builder tag(TagKey<Type> tag) {
            tags.add(tag);
            return (Builder)this;
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
