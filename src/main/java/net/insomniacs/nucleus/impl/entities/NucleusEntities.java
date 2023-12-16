package net.insomniacs.nucleus.impl.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class NucleusEntities {

    public static final EntityType<VilgerEntity> VILGER = Registry.register(
            Registries.ENTITY_TYPE,
            Nucleus.id("vilger"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, VilgerEntity::new).dimensions(EntityDimensions.fixed(1.0f, 2.0f)).build()
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(VILGER, VilgerEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1)
                .add(EntityAttributes.GENERIC_ARMOR, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1)
                .build());
    }

}
