package com.cycling.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @ClassName: RedisCacheManger
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 22:04
 * @Version: V1.0
 */
public class RedisCacheManger implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new RedisCache<>(cacheName);
    }
}
