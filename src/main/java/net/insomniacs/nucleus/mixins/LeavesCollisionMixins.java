package net.insomniacs.nucleus.mixins;

import net.insomniacs.nucleus.api.content.NucleusTags;
import net.minecraft.block.*;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("unused")
public class LeavesCollisionMixins {

	@Mixin(LeavesBlock.class)
	public static class LeavesBlockMixin extends Block {

		public LeavesBlockMixin(Settings settings) {
			super(settings);
		}

		@Override
		public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			if (canWalkThroughLeaves(context)) return VoxelShapes.empty();
			return super.getCollisionShape(state, world, pos, context);
		}

		@Override
		public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			if (canWalkThroughLeaves(context)) return VoxelShapes.empty();
			return super.getCameraCollisionShape(state, world, pos, context);
		}

		@Unique
		private boolean canWalkThroughLeaves(ShapeContext context) {
			if (!(context instanceof EntityShapeContext entityCollision)) return false;
			var entity = entityCollision.getEntity();

			if (entity == null) return false;
			return entity.getType().isIn(NucleusTags.CAN_WALK_THROUGH_LEAVES);
		}

	}

	@Mixin(RavagerEntity.class)
	public static class RavagerEntityMixin {

		@Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
		private boolean preventLeafBreaking(GameRules i, GameRules.Key<GameRules.BooleanRule> r) {
			return false;
		}

	}

}
