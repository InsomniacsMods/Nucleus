package net.insomniacs.nucleus.api.modreg;

import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ModEntry<
        Entry extends ModEntry<Entry, Builder, Value>,
        Builder extends ModEntry.EntryBuilder<Builder, Entry, Value>,
        Value
> {

    protected abstract Registry<Value> getRegistry();
    protected abstract Value generateValue();

    public Identifier getType() {
        return ((DefaultedRegistry<Value>)getRegistry()).getDefaultId();
    };

    public final Identifier id;

    protected final Value value;
    protected Builder settings;

    public Value value() {
        return value;
    }
    public Builder getSettings() {
        return settings;
    }


    @SuppressWarnings("RedundantCast")
    public ModEntry(Builder settings) {
        this.settings = settings;
        this.id = settings.id;
        this.value = (Value)register();
    }

    public boolean isTranslated() {
        return settings.defaultTranslation != null;
    }

    public String getTranslatedName() {
        return settings.defaultTranslation;
    }

    public List<TagKey<Value>> getTags() {
        return settings.tags;
    }

    protected Value register() {
        Value value = generateValue();
        if (value != null) Registry.register(getRegistry(), id, generateValue());
        return value;
    }

    @SuppressWarnings("unchecked")
    public static abstract class EntryBuilder <
            Builder extends EntryBuilder<Builder, Entry, Value>,
            Entry extends ModEntry<Entry, Builder, Value>,
            Value
    > {

        protected abstract Entry createEntry();

        protected final Identifier id;

        public EntryBuilder(Identifier id) {
            this.id = id;
}

        public Entry register() {
            Entry entry = createEntry();
            ModRegistry.addEntry(entry);
            return entry;
        }

        // Translation

        protected String defaultTranslation;

        public Builder translate(String name) {
            this.defaultTranslation = name;
            return (Builder)this;
        };

        public Builder translate() {
            return translate(this.titlecaseName());
        }

        public String titlecaseName() {
            return Arrays
                    .stream(this.id.getPath().split("_"))
                    .map(StringUtils::capitalize)
                    .collect(Collectors.joining(" "));
        }

        // Tags

        protected List<TagKey<Value>> tags = new ArrayList<>();

        public Builder tag(TagKey<Value> tag) {
            tags.add(tag);
            return (Builder)this;
        }

    }

}
