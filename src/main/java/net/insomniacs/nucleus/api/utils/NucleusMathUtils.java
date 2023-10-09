package net.insomniacs.nucleus.api.utils;

/**
 * Class for general math utilities
 * @author dragoncommands
 */
public class NucleusMathUtils {

    /**
     * Takes a comparable value and based off of a current value, a minimum value, and a maximum value returns a clamped value.
     * @param value current value being passed.
     * @param min the minimum limit a value is allowed to be.
     * @param max the maximum limit a value is allowed to be.
     * @return the clamped value.
     * @param <T> the type of value
     */
    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if(value.compareTo(max) > 0)
            return max;
        else if (value.compareTo(min) < 0)
            return min;
        else
            return value;
    }

}
