package net.insomniacs.nucleus.api.components;

/**
 * A standardized component for sanity provided by the Insomniacs API.
 */
public interface SanityComponent extends DataComponent {
    /**
     * Gets the sanity currently associated with the instance of the data component.
     * @return the current sanity level of the provider.
     */
    int getSanity();

    /**
     * Sets the current sanity level associated with the instance of the data component.
     */
    void setSanity(int sanity);
}
