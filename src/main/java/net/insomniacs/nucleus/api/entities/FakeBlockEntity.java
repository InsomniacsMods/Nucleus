package net.insomniacs.nucleus.api.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Predicate;

public abstract class FakeBlockEntity extends Entity {

	protected static final Predicate<Entity> PREDICATE = entity ->
			entity instanceof AbstractDecorationEntity || entity instanceof FakeBlockEntity;

	private int obstructionCheckCounter;
	protected Direction facing;
	private final SoundGroup soundGroup;

	public FakeBlockEntity(EntityType<? extends FakeBlockEntity> type, World world, SoundGroup soundGroup) {
		super(type, world);
		this.soundGroup = soundGroup;
		this.setFacing(Direction.SOUTH);
	}

	public FakeBlockEntity(EntityType<? extends FakeBlockEntity> type, World world) {
		this(type, world, SoundGroup.DEFAULT);
	}


	public void onBreak(Entity entity) {
		this.playSound(soundGroup.breakSound, 1.0F, 1.0F);
		this.emitGameEvent(GameEvent.BLOCK_CHANGE, entity);
	}

	public void onPlace() {
		this.playSound(soundGroup.placeSound, 1.0F, 1.0F);
	}

	public void setFacing(Direction facing) {
		if (facing.getAxis().isVertical()) return;
		this.facing = facing;
		this.setYaw((float)(this.facing.getHorizontal() * 90));
		this.prevYaw = this.getYaw();
	}

	public boolean canStayAttached() {
		return this.getWorld().getOtherEntities(this, this.getBoundingBox(), PREDICATE).isEmpty();
	}


	@Override
	public void tick() {
		if (this.getWorld().isClient) return;

		this.attemptTickInVoid();
		if (this.obstructionCheckCounter++ == 100) {
			this.obstructionCheckCounter = 0;
			if (!this.isRemoved() && !this.canStayAttached()) {
				this.discard();
				this.onBreak(null);
			}
		}
	}

	@Override
	public boolean canHit() {
		return true;
	}

	@Override
	public boolean handleAttack(Entity attacker) {
		if (!(attacker instanceof PlayerEntity playerEntity)) return false;
		if (!this.getWorld().canPlayerModifyAt(playerEntity, this.getBlockPos())) return false;
		return this.damage(this.getDamageSources().playerAttack(playerEntity), 0.0F);
	}

	@Override
	public Direction getHorizontalFacing() {
		return this.facing;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		System.out.println("A");
		if (this.isInvulnerableTo(source)) return false;

		if (!this.isRemoved() && !this.getWorld().isClient) {
			this.kill();
			this.scheduleVelocityUpdate();
			this.onBreak(source.getAttacker());
		}
		return true;
	}

	@Override
	public void move(MovementType movementType, Vec3d movement) {
		if (!this.getWorld().isClient && !this.isRemoved() && movement.lengthSquared() > 0.0) {
			this.kill();
			this.onBreak(null);
		}
	}

	@Override
	public void addVelocity(double deltaX, double deltaY, double deltaZ) {
		if (!this.getWorld().isClient && !this.isRemoved() && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.0) {
			this.kill();
			this.onBreak(null);
		}
	}

	@Override
	public float applyRotation(BlockRotation rotation) {
		if (this.facing.getAxis() != Direction.Axis.Y) {
			switch (rotation) {
				case CLOCKWISE_180: {
					this.facing = this.facing.getOpposite();
				}
				case COUNTERCLOCKWISE_90: {
					this.facing = this.facing.rotateYCounterclockwise();
				}
				case CLOCKWISE_90: {
					this.facing = this.facing.rotateYClockwise();
				}
			}
		}

		float f = MathHelper.wrapDegrees(this.getYaw());
		return switch (rotation) {
			case CLOCKWISE_180 -> f + 180.0F;
			case COUNTERCLOCKWISE_90 -> f + 90.0F;
			case CLOCKWISE_90 -> f + 270.0F;
			default -> f;
		};
	}


	@Override public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {}
	@Override public void calculateDimensions() {}
	@Override protected void initDataTracker(DataTracker.Builder builder) {}
	@Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
	@Override protected void writeCustomDataToNbt(NbtCompound nbt) {}


	public record SoundGroup (
			SoundEvent placeSound,
			SoundEvent breakSound,
			SoundEvent hitSound
	) {

		public static final SoundGroup DEFAULT = new SoundGroup(
				SoundEvents.ENTITY_ITEM_FRAME_PLACE,
				SoundEvents.ENTITY_ITEM_FRAME_BREAK,
				SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM
		);

	}

}
