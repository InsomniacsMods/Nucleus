package net.insomniacs.nucleus.mixins;

import net.insomniacs.nucleus.api.events.PlayerTickCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {


	@Inject(method = "tickMovement", at = @At("TAIL"))
	private void injected(CallbackInfo ci) {
		PlayerEntity self = (PlayerEntity)(Object)this;
		PlayerTickCallback.EVENT.invoker().tick(self.getWorld(), self);
	}

}
