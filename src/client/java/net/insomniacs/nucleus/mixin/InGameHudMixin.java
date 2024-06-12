package net.insomniacs.nucleus.mixin;

import net.insomniacs.nucleus.api.crosshair.CrosshairTextureCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Shadow @Final private MinecraftClient client;

	@ModifyArg(
			method = "renderCrosshair",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V")
	)
	private Identifier dynamicCrosshairTexture(Identifier texture) {
		return CrosshairTextureCallback.EVENT
				.invoker()
				.setTexture(client.world, client.player)
				.orElse(texture);
	}

}