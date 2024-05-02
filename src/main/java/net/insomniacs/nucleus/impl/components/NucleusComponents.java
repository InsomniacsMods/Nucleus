package net.insomniacs.nucleus.impl.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.components.*;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class NucleusComponents {

	public static final DataComponentType<BundleComponent> BUNDLE = register("bundle",
			BundleComponent.CODEC
	);

	public static final DataComponentType<SoulboundComponent> SOULBOUND = register("soulbound",
			SoulboundComponent.CODEC
	);

	public static final DataComponentType<LocationBindingComponent> BOUND_LOCATION = register("bound_location",
			LocationBindingComponent.CODEC
	);

	public static final DataComponentType<FontChangingComponent> FONT_CHANGING = register("font_changing",
			FontChangingComponent.CODEC
	);

	public static final DataComponentType<InfoTooltipComponent> INFO = register("info",
			InfoTooltipComponent.CODEC
	);


	public static void init() {
		ComponentTooltipRegistry.register(
				NucleusComponents.SOULBOUND,
				NucleusComponents.BOUND_LOCATION,
				NucleusComponents.FONT_CHANGING
		);
	}


	public static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
		return Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				Nucleus.of(id),
				builderOperator.apply(DataComponentType.builder()).cache().build()
		);
	}

	public static <T> DataComponentType<T> register(String id, Codec<T> codec, PacketCodec<ByteBuf, T> packetCodec) {
		return register(id, builder -> builder.codec(codec).packetCodec(packetCodec));
	}

	public static <T> DataComponentType<T> register(String id, Codec<T> codec) {
		return register(id, codec, PacketCodecs.codec(codec));
	}

}
