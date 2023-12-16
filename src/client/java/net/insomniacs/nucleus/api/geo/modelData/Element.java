package net.insomniacs.nucleus.api.geo.modelData;

import net.insomniacs.nucleus.api.util.Vec3i;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;

public abstract class Element {
    public Vec3i pivot;
    public Vec3i rotation;

    public boolean visible;
    public boolean mirror;
}