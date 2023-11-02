package net.insomniacs.nucleus.impl.components;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.api.components.ComponentKeys;
import net.insomniacs.nucleus.api.components.InsightComponentInstance;
import net.insomniacs.nucleus.api.components.SanityComponentInstance;

public class ComponentInitializer implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ComponentKeys.SANITY, SanityComponentInstance::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(ComponentKeys.INSIGHT, InsightComponentInstance::new, RespawnCopyStrategy.ALWAYS_COPY);
        if(FabricLoader.getInstance().isModLoaded("manic")) {
            System.out.println("Manic is loaded.");
        }
    }
}
