package net.insomniacs.nucleus.api.crosshair;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public interface CrosshairTextureCallback {

	Event<CrosshairTextureCallback> EVENT = EventFactory.createArrayBacked(
			CrosshairTextureCallback.class,
			(w, p) -> Optional.empty(),
			CrosshairTextureCallback::processInvokers
	);


	static CrosshairTextureCallback processInvokers(CrosshairTextureCallback[] listeners) {
		return (world, player) -> {
			for (CrosshairTextureCallback event : listeners) {
				var texture = event.setTexture(world, player);
				if (texture.isPresent()) return texture;
			}
			return Optional.empty();
		};
	}


	Optional<Identifier> setTexture(World world, PlayerEntity player);

}
