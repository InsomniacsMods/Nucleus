package net.insomniacs.nucleus.api.geo_model.data;

import net.insomniacs.nucleus.utils.Vec3i;

public abstract class Element {
    public Vec3i pivot;
    public Vec3i rotation;

    public boolean visible;
    public boolean mirror;
}