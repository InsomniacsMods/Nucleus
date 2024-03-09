package net.insomniacs.nucleus.impl.entities;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class SpookEntityModel extends EntityModel<VilgerEntity> {

	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart arm_right;
	private final ModelPart arm_left;

	public SpookEntityModel(ModelPart root) {
        this.head = root.getChild("head");
		this.torso = root.getChild("torso");
		this.arm_right = root.getChild("arm_right");
		this.arm_left = root.getChild("arm_left");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 12).cuboid(
				-3.5F, -3.95F, -7.6F,
				7.0F, 4.0F, 7.0F,
				new Dilation(0.0F)
		)
		.uv(21, 12).cuboid(
				-3.5F, 0.05F, -7.6F,
				7.0F, 1.0F, 0.0F,
				new Dilation(0.0F)
		), ModelTransform.pivot(
				0.0F, 8.95F, -2.9F
		));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create(), ModelTransform.pivot(
				0.0F, 0.2901F, -0.3547F
		));

		ModelPartData jaw_r1 = jaw.addChild("jaw_r1", ModelPartBuilder.create().uv(21, 12).cuboid(
				-3.5F, -1.0F, -7.0F,
				7.0F, 1.0F, 0.0F, new Dilation(0.0F)
		)
		.uv(21, 16).cuboid(
				-3.5F, 0.0F, -7.0F,
				7.0F, 2.0F, 7.0F,
				new Dilation(0.0F)
		), ModelTransform.of(
				0.0F, -0.2401F, -0.2453F,
				0.3927F, 0.0F, 0.0F
		));

		ModelPartData tongue = jaw.addChild("tongue", ModelPartBuilder.create(), ModelTransform.of(
				0.0F, -0.4071F, -1.713F,
				-0.1047F, 0.0F, 0.0F)
		);

		ModelPartData tongue_r1 = tongue.addChild("tongue_r1", ModelPartBuilder.create().uv(29, 0).cuboid(
				-1.5F, 0.0F, -7.0F,
				4.0F, 0.0F, 7.0F,
				new Dilation(0.0F)
		), ModelTransform.of(
				-0.5F, 0.067F, 0.4677F,
		0.3927F, 0.0F, 0.0F
		));

		ModelPartData tongue_drop = tongue.addChild("tongue_drop", ModelPartBuilder.create(), ModelTransform.pivot(
				0.0F, 2.7936F, -5.9341F
		));

		ModelPartData tongue_drop_r1 = tongue_drop.addChild("tongue_drop_r1", ModelPartBuilder.create().uv(36, 7).cuboid(
				-2.0F, -0.0691F, -0.0421F,
				4.0F, 4.0F, 0.0F,
				new Dilation(0.0F)
		), ModelTransform.of(
				0.0F, 0.0F, 0.0F,
				0.3927F, 0.0F, 0.0F
		));

		ModelPartData torso = modelPartData.addChild("torso", ModelPartBuilder.create(), ModelTransform.pivot(
				0.0F, 11.4134F, 0.1194F
		));

		ModelPartData torso_r1 = torso.addChild("torso_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -1.5F, 0.5F, 9.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(
				0.0F, -1.9134F, -4.6194F,
				-0.3927F, 0.0F, 0.0F
		));

		ModelPartData tatters = torso.addChild("tatters", ModelPartBuilder.create(), ModelTransform.pivot(
				0.0F, 1.1681F, 4.1169F
		));

		ModelPartData tatters_r1 = tatters.addChild("tatters_r1", ModelPartBuilder.create().uv(0, 23).cuboid(-3.5F, 0.0F, -3.0F, 7.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.8315F, 0.7637F, 0.3927F, 0.0F, 0.0F));

		ModelPartData arm_right = modelPartData.addChild("arm_right", ModelPartBuilder.create().uv(32, 25).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 14.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 10.0F, -3.0F));

		ModelPartData arm_left = modelPartData.addChild("arm_left", ModelPartBuilder.create().uv(20, 25).cuboid(
				0.0F, 0.0F, -1.0F,
				3.0F, 14.0F, 3.0F,
				new Dilation(0.0F)
		), ModelTransform.pivot(
				4.0F, 10.0F, -3.0F
		));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(VilgerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		torso.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

}