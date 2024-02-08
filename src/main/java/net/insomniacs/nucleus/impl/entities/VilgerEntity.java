package net.insomniacs.nucleus.impl.entities;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VilgerEntity extends PassiveEntity {

    public final AnimationState spinningHeadAnimationState = new AnimationState();

    public VilgerEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public void onDamaged(DamageSource source) {
        super.onDamaged(source);
        spinningHeadAnimationState.startIfNotRunning(this.age);
    }

}
