package net.insomniacs.nucleus.api.modreg.utils;

import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public record CustomRegistrySupplier<B, S, T>(BiFunction<Identifier, Function<S, T>, B> builderConstructor, String modID) {

    public B create(String id, Function<S, T> constructor) {
        return builderConstructor.apply(new Identifier(modID, id), constructor);
    }

}