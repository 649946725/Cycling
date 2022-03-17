package com.cycling.cache;

import com.cycling.utils.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import java.util.Collection;
import java.util.Set;

/**
 * @ClassName: RedisCache
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 22:10
 * @Version: V1.0
 */

public class RedisCache<K,V> implements Cache<K,V> {

    private String cacheName;

    public RedisCache() {

    }
    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
      return (V) RedisUtil.hget(this.cacheName,k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        RedisUtil.hset(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        Object v = RedisUtil.hget(this.cacheName, k.toString());
        RedisUtil.del(this.cacheName,k.toString());
        return (V) v;
    }

    @Override
    public void clear() throws CacheException {
        RedisUtil.del(this.cacheName);
    }

    @Override
    public int size() {
        return (int) RedisUtil.hGetHsetSize(this.cacheName);
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) RedisUtil.hKeys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>) RedisUtil.hValues(this.cacheName);
    }
}
