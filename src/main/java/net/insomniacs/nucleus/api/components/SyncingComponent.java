package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import io.netty.util.internal.UnstableApi;

// TODO fully implement this.
/**
 * Represents a component that can be synced.
 * Requires a provided Codec which will be synced via packet dispatch when possible.
 * @param <T> the class type to be converted for data syncing
 * @author dragoncommands
 */
public interface SyncingComponent<T> {
    Codec<T> getCodec();
}
