package net.insomniacs.nucleus.mixin;

import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin {

	@Redirect(
			method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
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
