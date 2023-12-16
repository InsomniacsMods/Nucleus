package net.insomniacs.nucleus.api.blockbench.modelData;

import net.insomniacs.nucleus.api.util.Vec3i;
import net.minecraft.client.model.ModelPartBuilder;

public abstract class Element {
    public Group parent;

    public String name;
    public String uuid;

    public Vec3i pivotPoint;
    public Vec3i rotation;

    public boolean visible;
    public boolean mirror;

    public abstract ModelPartBuilder modelData();

    public void update() {
        if (this.parent == null) return;
        this.pivotPoint = new Vec3i(
                this.parent.pivotPoint.x + this.pivotPoint.x,
                this.parent.pivotPoint.y + this.pivotPoint.y,
                this.parent.pivotPoint.z + this.pivotPoint.z
        );
        this.rotation = new Vec3i(
                this.parent.rotation.x + this.rotation.x,
                this.parent.rotation.y + this.rotation.y,
                this.parent.rotation.z + this.rotation.z
        );
        this.visible = this.parent.visible;
        this.mirror = this.parent.mirror;
    }

}