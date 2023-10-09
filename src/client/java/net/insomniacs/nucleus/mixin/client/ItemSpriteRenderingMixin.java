package net.insomniacs.nucleus.mixin.client;

import net.insomniacs.nucleus.api.markers.ItemDisplayMarker;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ItemRenderer.class)
public class ItemSpriteRenderingMixin {

    @Final @Shadow private ItemModels models;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"))
    private void nucleus$renderItemInject(ItemStack itemStack, ModelTransformationMode itemDisplayContext, boolean bl, MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo ci) {

        if(itemStack.getItem() instanceof ItemDisplayMarker && Nucleus$getInventoryModel(itemDisplayContext, itemStack.getItem()) != null) {
            bakedModel = this.models.getModelManager().getModel(Nucleus$getInventoryModel(itemDisplayContext, itemStack.getItem()));
        }
    }

    /**
     * Allows for rendering 3D items as 2D items.
     *
     * @param context
     * @param item
     * @return
     */
    @Unique
    ModelIdentifier Nucleus$getInventoryModel(ModelTransformationMode context, Item item) {
        boolean render2D = (context == ModelTransformationMode.GUI || context == ModelTransformationMode.GROUND || context == ModelTransformationMode.FIXED);
        Optional<RegistryKey<Item>> registryKey = Registries.ITEM.getKey(item);
        if (registryKey.isEmpty()) return null;
        Identifier location = registryKey.get().getRegistry();
        if (render2D) {
            return new ModelIdentifier(location.getNamespace(), location.getPath(), "_inventory");
        } else {
            return null;
        }
    }

}
