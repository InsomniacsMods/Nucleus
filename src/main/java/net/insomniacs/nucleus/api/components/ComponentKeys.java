package net.insomniacs.nucleus.api.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.minecraft.util.Identifier;

public class ComponentKeys {
    public static final ComponentKey<SanityComponent> SANITY = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("manic","sanity"), SanityComponent.class);
    public static final ComponentKey<InsightComponent> INSIGHT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("manic","insight"), InsightComponent.class);
}
