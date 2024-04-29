package net.insomniacs.nucleus.api.animationLoader.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum LoopMode implements StringIdentifiable {

	ONCE(false),
	HOLD("hold_on_last_frame"),
	LOOP(true);

	public static final Codec<LoopMode> CODEC = StringIdentifiable.createCodec(LoopMode::values);


	final Object value;

	LoopMode(
			Object value
	) {
		this.value = value;
	}

	@Override
	public String asString() {
		return value.toString();
	}

}
