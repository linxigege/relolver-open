package com.flier.core.infrastructure.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 构建缓存
 *
 * @author mac
 */
public class CacheBuilder {
    private static final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    public static Cache getOrCreateCache(String key) {
        if (!cacheMap.contains(key)) {
            cacheMap.put(key, new Cache());
        }
        return cacheMap.get(key);
    }
}
