package net.insomniacs.nucleus.mixin;

import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {

	@Redirect(
			method = "renderArmor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ArmorItem;getSlotType()Lnet/minecraft/entity/EquipmentSlot;"
			)
	)
	private EquipmentSlot fallbackFakeHelmetRendering(ArmorItem item) {
		if (item.renderArmorLayer()) return item.getSlotType();
		return null;
	}

}
