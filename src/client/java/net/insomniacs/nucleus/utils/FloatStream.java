package net.insomniacs.nucleus.utils;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

import java.util.stream.IntStream;

public class FloatStream {



    PrimitiveCodec<IntStream> CODEC = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<IntStream> read(final DynamicOps<T> ops, final T input) {
            return ops
                    .getIntStream(input);
        }

        @Override
        public <T> T write(final DynamicOps<T> ops, final IntStream value) {
            return ops.createIntList(value);
        }

        @Override
        public String toString() {
            return "IntStream";
        }
    };

}
