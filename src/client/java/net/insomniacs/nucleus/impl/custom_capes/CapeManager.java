package net.insomniacs.nucleus.impl.custom_capes;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.util.Identifier;

import java.util.List;

public class CapeManager {

	public static final List<Identifier> POSSIBLE_CAPES = List.of(
			Nucleus.of("test"),
			Nucleus.of("fancy")
	);

	public static List<Identifier> getPossibleCapes() {
		return POSSIBLE_CAPES;
	}

}
