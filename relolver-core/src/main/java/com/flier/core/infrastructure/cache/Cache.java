package com.flier.core.infrastructure.cache;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 缓存
 *
 * @author user
 */
public class Cache {
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> cacheTimeout = new ConcurrentHashMap<>();

    public Cache() {
        this.startCleaner();
    }

    public void cache(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        if (cacheTimeout.contains(key)) {
            if (isTimeOut(key)) {
                return null;
            } else {
                return cache.get(key);
            }
        } else {
            return cache.get(key);
        }
    }

    private boolean isTimeOut(String key) {
        return System.currentTimeMillis() > cacheTimeout.get(key);
    }

    public void cache(String key, Object value, long timeout) {
        cache.put(key, value);
        cacheTimeout.put(key, System.currentTimeMillis() + timeout);
    }

    private void startCleaner() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    cleanCache();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        singleThreadPool.shutdown();
    }

    private void cleanCache() {
        List<String> timeOutKeys = new ArrayList<String>();
        for (Map.Entry<String, Long> entry : cacheTimeout.entrySet()) {
            long out = entry.getValue();
            if (System.currentTimeMillis() > out) {
                timeOutKeys.add(entry.getKey());
            }
        }

        for (String timeOutKey : timeOutKeys) {
            cache.remove(timeOutKey);
            cacheTimeout.remove(timeOutKey);
        }
    }
}
