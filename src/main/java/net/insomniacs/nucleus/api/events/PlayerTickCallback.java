package net.insomniacs.nucleus.api.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Arrays;

public interface PlayerTickCallback {

	Event<PlayerTickCallback> EVENT = EventFactory.createArrayBacked(PlayerTickCallback.class,
			listeners -> (world, player) -> Arrays.stream(listeners).forEach(listener -> listener.tick(world, player))
	);

	void tick(World world, PlayerEntity player);

}
