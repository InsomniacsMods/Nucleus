package net.insomniacs.nucleus.mixin.custom_capes;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

	@Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
	private void endlessEncore$uniqueCapeTextures(CallbackInfoReturnable<SkinTextures> cir) {
		Identifier id = Nucleus.of("textures/entity/player/capes/test.png");
		SkinTextures result = cir.getReturnValue();
		result = new SkinTextures(
				result.texture(),
				result.textureUrl(),
				id,
				result.elytraTexture(),
				result.model(),
				result.secure()
		);
		cir.setReturnValue(result);
	}

}
