package net.insomniacs.nucleus.api.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

/**
 * A standardized component for sanity provided by the Insomniacs API.
 */
public interface SanityComponent extends ComponentV3 {
    /**
     * Gets the sanity currently associated with the instance of the data component.
     * @return the current sanity level of the provider.
     */
    int getSanity();
    void setSanity(int sanity);

}
