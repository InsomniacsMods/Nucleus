package net.insomniacs.nucleus.api.utils;

import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class FakeBlockEntityRenderer<T extends Entity> extends EntityRenderer<T> {

	private final BlockRenderManager blockRenderManager;

	public FakeBlockEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.blockRenderManager = context.getBlockRenderManager();
	}


	@Override
	public void render(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider provider, int i) {
		if (entity.isInvisible()) return;
		super.render(entity, f, g, matrixStack, provider, i);

		BakedModelManager manager = this.blockRenderManager.getModels().getModelManager();
		ModelIdentifier id = this.getModelId(entity);
		BakedModel model = manager.getModel(id);
		VertexConsumer consumer = provider.getBuffer(getLayer(entity));



		matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - entity.getYaw()));
		matrixStack.translate(-0.5F, 0, -0.5);

		this.blockRenderManager.getModelRenderer().render(
				matrixStack.peek(), consumer, null, model,
				1.0F, 1.0F, 1.0F, i, OverlayTexture.DEFAULT_UV
		);
		matrixStack.pop();

	}


	@Override
	public Identifier getTexture(T entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}

	public ModelIdentifier getModelId(T entity) {
		var id = Registries.ENTITY_TYPE.getId(entity.getType());
		return new ModelIdentifier(id, getVariant(entity));
	}

	public String getVariant(T entity) {
		return "";
	}

	public RenderLayer getLayer(T entity) {
		return TexturedRenderLayers.getEntityCutout();
	}

}
