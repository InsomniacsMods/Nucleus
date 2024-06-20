package net.insomniacs.nucleus.mixin;

import net.insomniacs.nucleus.api.items.NucleusBundleTooltipData;
import net.insomniacs.nucleus.impl.tooltip.NucleusBundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {

	@Inject(
			method = "of(Lnet/minecraft/item/tooltip/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void returnCustomComponent(TooltipData tooltipData, CallbackInfoReturnable<TooltipComponent> cir) {
		if (tooltipData instanceof NucleusBundleTooltipData data) {
			var result = new NucleusBundleTooltipComponent(data.component());
			cir.setReturnValue(result);
		}
	}

}
