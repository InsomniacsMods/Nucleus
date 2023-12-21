package net.insomniacs.nucleus.impl.entities;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VilgerEntityRenderer extends MobEntityRenderer<VilgerEntity, VilgerEntityModel> {

    private final Identifier entityId;

    public VilgerEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer modelLayer, Identifier entityId) {
        super(context, new VilgerEntityModel(context.getPart(modelLayer)), 0.5f);
        this.entityId = entityId;
    }

    @Override
    public Identifier getTexture(VilgerEntity entity) {
        return this.entityId.withPrefixedPath("textures/entity/").withSuffixedPath(".png");
    }

}
