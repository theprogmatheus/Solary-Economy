package com.redeskyller.bukkit.solaryeconomy.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Data
public class LocalCache<K, V> {

    private final ConcurrentHashMap<K, CacheEntry<V>> cacheMap;
    private final long expireTime;
    private final Function<K, V> loader;

    public LocalCache(final long expireTime, final Function<K, V> loader) {
        this.cacheMap = new ConcurrentHashMap<>();
        this.expireTime = expireTime;
        this.loader = loader;
    }

    public V get(K key) {
        CacheEntry<V> entry = cacheMap.get(key);
        if (entry != null) {
            long now = System.currentTimeMillis();
            if ((now - entry.timestamp) < expireTime) {
                return entry.value;
            }
        }
        // Isso aqui está em sync, pode causar lag spike.
        // farei isso depois.
        if (this.loader != null) {
            V value = this.loader.apply(key);
            if (value != null)
                return this.put(key, value);
        }
        return null;
    }

    public V put(K key, V value) {
        this.cacheMap.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
        return value;
    }

    public void cleanUp() {
        long now = System.currentTimeMillis();
        this.cacheMap.entrySet().removeIf(entry ->
                (now - entry.getValue().timestamp) >= expireTime);
    }


    @RequiredArgsConstructor
    public static class CacheEntry<T> {
        public final T value;
        public final long timestamp;
    }

}
