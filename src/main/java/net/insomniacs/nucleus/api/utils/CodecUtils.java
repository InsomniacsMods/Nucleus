package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Iterator;

public class CodecUtils {

	public static <Type> Codec<Type> merge(
			Codec<? extends Type> codec1,
			Codec<? extends Type> codec2
	) {
		return Codec.either(codec1, codec2).flatComapMap(
				either -> {
					if (either.left().isPresent()) return either.left().get();
					if (either.right().isPresent()) return either.right().get();
					return null;
				}, null
		);
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <Type> Codec<Type> merge(
			Codec<? extends Type>... codecs
	) {
		Iterator<Codec<? extends Type>> iterator = Arrays.stream(codecs).iterator();
		Codec<Type> base = (Codec<Type>)iterator.next();
		while (iterator.hasNext()) base = merge(base, iterator.next());
		return base;
	}

}
