package net.insomniacs.nucleus.api.modreg.utils;

import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public record SettingCustomRegistrySupplier <
        Builder,
        Settings,
        Type
> (BiFunction<Identifier, Function<Settings, Type>, Builder> builderConstructor, String modID) {

    public Builder create(String id, Function<Settings, Type> constructor) {
        return builderConstructor.apply(new Identifier(modID, id), constructor);
    }

}