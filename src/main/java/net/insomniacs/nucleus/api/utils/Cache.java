package net.insomniacs.nucleus.api.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    public Cache(int max) {
        this.maxSize = max;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > this.maxSize;
    }

}