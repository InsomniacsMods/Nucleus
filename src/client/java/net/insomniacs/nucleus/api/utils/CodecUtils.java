package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;

import java.util.Arrays;
import java.util.Iterator;

public class CodecUtils {

	public static <Type> Codec<Type> combine(
			Codec<Type> codec1,
			Codec<Type> codec2
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
	public static <Type> Codec<Type> combine(
			Codec<Type>... codecs
	) {
		Iterator<Codec<Type>> iterator = Arrays.stream(codecs).iterator();
		Codec<Type> base = iterator.next();
		while (iterator.hasNext()) base = combine(base, iterator.next());
		return base;
	}

}
