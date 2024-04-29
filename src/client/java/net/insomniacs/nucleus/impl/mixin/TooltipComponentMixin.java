package net.insomniacs.nucleus.impl.mixin;

import net.insomniacs.nucleus.api.items.CustomBundleTooltipData;
import net.insomniacs.nucleus.impl.tooltip.CustomBundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {

	@Inject(method = "of(Lnet/minecraft/client/item/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
	private static void returnCustomComponent(TooltipData tooltipData, CallbackInfoReturnable<TooltipComponent> cir) {
		if (tooltipData instanceof CustomBundleTooltipData data) {
			var result = new CustomBundleTooltipComponent(data.component());
			cir.setReturnValue(result);
		}
	}

}
