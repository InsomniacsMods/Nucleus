package net.insomniacs.nucleus.api.registries;

import net.insomniacs.nucleus.api.components.TickingComponent;
import net.insomniacs.nucleus.impl.items.HasPredicates;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import static net.insomniacs.nucleus.Nucleus.getLocation;

public class Registries {

    public static final RegistryKey<Registry<TickingComponent>> TICKING_COMPONENTS = getKey("ticking_components");
    public static final RegistryKey<Registry<HasPredicates>> PREDICATE_ITEMS = Registries.getKey("predicate_items");

    /**
     * Method to create a registry key for a given ID.
     * @param name key id
     * @return key value
     * @param <T> typeof key
     */
    public static <T> RegistryKey<Registry<T>> getKey(String name) {
        return RegistryKey.ofRegistry(getLocation(name));
    }
}
