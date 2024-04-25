package net.insomniacs.nucleus.impl.mixin;

import net.insomniacs.nucleus.api.tooltipLoader.ModTooltipLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow public abstract Item getItem();

	@Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<List<Text>> cir) {
		List<Text> result = cir.getReturnValue();
		Identifier itemId = Registries.ITEM.getId(getItem());
		Text modTooltip = ModTooltipLoader.INSTANCE.getTooltip(itemId.getNamespace());
		if (modTooltip.getString().isEmpty()) return;

		result.add(modTooltip);
		cir.setReturnValue(result);
	}

}
