package net.insomniacs.nucleus.api.modreg.provider;

import net.minecraft.util.Identifier;

public class Entry<T, B> {

    private final Identifier id;
    private final T value;
    private B settings;

    public Entry(Identifier id, T value, B settings) {
        this.id = id;
        this.value = value;
        this.settings = settings;
    }

    public T value() {
        return value;
    }

    public Identifier id() {
        return id;
    }

    public B settings() {
        return settings;
    }


    public Entry<T, B> copySettings(Entry<T, B> entry) {
        this.settings = entry.settings;
        return this;
    }

}
