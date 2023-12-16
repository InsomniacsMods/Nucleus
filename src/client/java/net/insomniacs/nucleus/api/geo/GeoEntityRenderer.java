package net.insomniacs.nucleus.api.geo;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class GeoEntityRenderer<T extends LivingEntity> extends LivingEntityRenderer<T, EntityModel<T>> {

    public final Identifier entityID;

    protected GeoEntityRenderer(EntityRendererFactory.Context ctx, Identifier entityID) {
        super(ctx, new GeoEntityModel<>(entityID), 0);
        this.entityID = entityID;
    }

    @Override
    public Identifier getTexture(T entity) {
        return this.entityID.withPrefixedPath("textures/entity/").withSuffixedPath(".png");
    }

}
