package net.insomniacs.nucleus.mixin.client;

import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPartData.class)
public interface ModelPartDataAccessor {
    @Accessor
    ModelTransform getRotationData();
}
