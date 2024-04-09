package net.insomniacs.nucleus.api.modreg;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class SettingsModEntry<
        Entry extends SettingsModEntry<Entry, Builder, Settings, Value>,
        Builder extends SettingsModEntry.EntryBuilder<Builder, Entry, Settings, Value>,
        Settings,
        Value
> extends ModEntry<Entry, Builder, Value> {

    public SettingsModEntry(Builder settings) {
        super(settings);
    }

    @Override
    @Nullable
    protected Value generateValue() {
        try {
            return settings.construct();
        } catch (MissingConstructorException exception) {
            Nucleus.LOGGER.error(exception.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static abstract class EntryBuilder <
            Builder extends EntryBuilder<Builder, Entry, Settings, Value>,
            Entry extends SettingsModEntry<Entry, Builder, Settings, Value>,
            Settings,
            Value
    > extends ModEntry.EntryBuilder<Builder, Entry, Value> {

        protected abstract Settings generateSettings();

        protected Function<Settings, Value> constructor;
        protected Settings defaultSettings;

        public EntryBuilder(Identifier id) {
            super(id);
            this.defaultSettings = generateSettings();
        }

        public EntryBuilder(Identifier id, Function<Settings, Value> constructor) {
            this(id);
            setConstructor(constructor);
        }

        public Builder setConstructor(Function<Settings, Value> constructor) {
            this.constructor = constructor;
            return (Builder)this;
        }

        public Builder modifySettings(UnaryOperator<Settings> modifier) {
            this.defaultSettings = modifier.apply(this.defaultSettings);
            return (Builder)this;
        }

        protected Value construct() throws MissingConstructorException {
            if (this.constructor == null) throw new MissingConstructorException(this);
            return constructor.apply(defaultSettings);
        }

    }

    public static class MissingConstructorException extends Exception {
        public MissingConstructorException(SettingsModEntry.EntryBuilder<?, ?, ?, ?> entry) {
            super("Mod Entry '%s' does not have a constructor".formatted(entry.id));
        }
    }

}
