package net.insomniacs.nucleus.api.modreg.utils;

import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public record CustomRegistrySupplier<Builder, Type>(Function<Identifier, Builder> builderConstructor, String modID) {

    public Builder create(String id) {
        return builderConstructor.apply(new Identifier(modID, id));
    }

}