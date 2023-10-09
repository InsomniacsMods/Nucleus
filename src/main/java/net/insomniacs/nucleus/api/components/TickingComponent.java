package net.insomniacs.nucleus.api.components;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;

/**
 * This is a class to define a ticking server component.
 */
@Environment(EnvType.SERVER)
public interface TickingComponent {

    /**
     * A method ticked on every ticking server component.
     * @param server an instance of the currently running server.
     */
    void tick(MinecraftServer server);

    /**
     * Returns a delay count for a ticking task. By default it always returns 0, for a constantly ticking component.
     * @return the delay reset.
     */
    default long getDelay() {
        return 0;
    }

}
