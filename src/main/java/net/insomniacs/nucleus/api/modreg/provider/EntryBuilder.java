package net.insomniacs.nucleus.api.modreg.provider;

import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class EntryBuilder<E extends Entry<?, ?>, S, T> {

    public final Identifier id;
    private S defaultSettings;
    private final Function<S, T> constructor;
    private final Registry<T> registry;

    public EntryBuilder(Identifier id, Function<S, T> constructor, S defaultSettings, Registry<T> registry) {
        this.id = id;
        this.defaultSettings = defaultSettings;
        this.constructor = constructor;
        this.registry = registry;
    }

    public EntryBuilder<E, S, T> modifySettings(UnaryOperator<S> modifier) {
        this.defaultSettings = modifier.apply(this.defaultSettings);
        return this;
    }

    protected T construct() {
        T value = constructor.apply(defaultSettings);
        return Registry.register(registry, id, value);
    }

    public abstract E createEntry();
    
    public E register() {
        var entry = createEntry();
        ModRegistry.ENTRIES.add(entry);
        return entry;
    }

}
