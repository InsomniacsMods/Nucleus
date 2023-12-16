package net.insomniacs.nucleus.impl.entities;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo.GeoEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class VilgerEntityRenderer extends GeoEntityRenderer<VilgerEntity> {
    public VilgerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, Nucleus.id("vilger"));
    }
}
