package net.insomniacs.nucleus.asm;

import net.minecraft.util.Identifier;

public interface NucleusGameOptions {

	default Identifier getCapeTexture() {
		return Identifier.tryParse("");
	}

	default void setCapeTexture(Identifier id) {}

}
