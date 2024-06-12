package net.insomniacs.nucleus.asm;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public interface NucleusItem {

	default Optional<Identifier> crosshairTexture(World world, PlayerEntity player) {
		return Optional.empty();
	}

}
