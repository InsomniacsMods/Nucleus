package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.entities.FakeBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FakeBlockEntityItem extends Item {

	private final EntityType<? extends FakeBlockEntity> entityType;

	public FakeBlockEntityItem(EntityType<? extends FakeBlockEntity> type, Settings settings) {
		super(settings);
		this.entityType = type;
	}


	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockPos clickedPos = pos.offset(direction);
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getStack();

		if (player == null) return ActionResult.FAIL;
		if (!this.canPlaceOn(player, direction, stack, clickedPos)) return ActionResult.FAIL;

		World world = context.getWorld();
		FakeBlockEntity entity = entityType.create(world);
		if (entity == null) return ActionResult.FAIL;
		entity.setFacing(player.getFacing().getOpposite());

		entity.setPos(clickedPos.getX()+0.5, clickedPos.getY(), clickedPos.getZ()+0.5);

		if (!entity.canStayAttached()) return ActionResult.CONSUME;

		if (!world.isClient) {
			world.emitGameEvent(player, GameEvent.ENTITY_PLACE, entity.getPos());
			world.spawnEntity(entity);
		}
		entity.onPlace();
		stack.decrementUnlessCreative(1, player);
		return ActionResult.success(world.isClient);
	}

	protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
		return player.canPlaceOn(pos, side, stack);
	}

}
