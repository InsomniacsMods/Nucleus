package net.insomniacs.nucleus.impl.components;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;

public class ComponentInitializer implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
//        registry.registerForPlayers(ComponentKeys.SANITY, player -> new SanityComponent(), RespawnCopyStrategy.NEVER_COPY);
    }
}
