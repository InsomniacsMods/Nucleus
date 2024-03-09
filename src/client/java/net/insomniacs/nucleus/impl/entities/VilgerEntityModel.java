package net.insomniacs.nucleus.impl.entities;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo_animation.GeoAnimationLoader;
import net.insomniacs.nucleus.api.geo_animation.data.GeoAnimationHandler;
import net.insomniacs.nucleus.api.geo_model.GeoModelLoader;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VilgerEntityModel extends SinglePartEntityModel<VilgerEntity> {


    public final ModelPart root;

    public VilgerEntityModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getModelData(Identifier entityId) {
        return GeoModelLoader.getEntity(entityId);
    }

    @Override
    public void setAngles(VilgerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

}
