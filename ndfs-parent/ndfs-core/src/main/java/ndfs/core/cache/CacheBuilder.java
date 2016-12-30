
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheLoader;

import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;

/**
 * <p>
 * Description:
 * 用于构造支持并发的缓存，共有两种缓存实现。一是基于guava缓存，功能较多。而是ConcurrentHashMap，可能速度较快。
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月18日 下午2:45:33
 */
public abstract class CacheBuilder<K, V> {
    public static Class DEFAULT_CACHE_BUILDER_TYPE = GuavaCacheBuilder.class;
    public static Class GUAVA_CACHE_BUILDER_TYPE = GuavaCacheBuilder.class;
    public static Class MAP_CACHE_BUILDER_TYPE = MapCacheBuilder.class;

    @SuppressWarnings("unchecked")
    public static <A, B> CacheBuilder<A, B> newBuilder(Class<?> builderType, Class<A> keyType, Class<B> valueType) {
        try {
            return (CacheBuilder<A, B>) builderType.newInstance();
        } catch (Exception e) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR,
                    "newBuilder 方法错误, builderType=%s, keyType=%s, valueType=%s", builderType.getName(),
                    keyType.getName(), valueType.getName());
        }
    }

    public abstract CacheBuilder<K, V> maxinumSize(Long size);

    public abstract CacheBuilder<K, V> initialCapacity(int initialCapacity);

    public abstract LocalCache<K, V> build();
}

class GuavaCacheBuilder<K, V> extends CacheBuilder<K, V> {

    @SuppressWarnings("unchecked")
    com.google.common.cache.CacheBuilder<K, V> guavaCacheBuilder = (com.google.common.cache.CacheBuilder<K, V>) com.google.common.cache.CacheBuilder
            .newBuilder();

    public CacheBuilder<K, V> maxinumSize(Long size) {
        guavaCacheBuilder = guavaCacheBuilder.maximumSize(size).recordStats();
        return this;
    }

    public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
        guavaCacheBuilder = guavaCacheBuilder.initialCapacity(initialCapacity);
        return this;
    }

    public LocalCache<K, V> build() {
        return new GuavaLocalCache<K, V>().build(guavaCacheBuilder);
    }

    static class GuavaLocalCache<K, V> implements LocalCache<K, V> {

        private com.google.common.cache.LoadingCache<K, V> loadingCache;

        public LocalCache<K, V> build(com.google.common.cache.CacheBuilder<K, V> guavaCacheBuilder) {
            loadingCache = guavaCacheBuilder.build(new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    return null;
                }
            });
            return this;
        }

        @Override
        public void put(K key, V value) {
            loadingCache.put(key, value);
        }

        @Override
        public V get(K key) {
            V v = loadingCache.getIfPresent(key);
            return v;
        }
        
        @Override
        public void remove(K key) {
            loadingCache.invalidate(key);
        }

        @Override
        public boolean contains(K key) {
            
            
            return loadingCache.getIfPresent(key) == null ? false : true;
        }
        
        @Override
        public boolean removeValue(V value) {
            boolean hasValue = false;
            Set<Map.Entry<K, V>> entrySet = loadingCache.asMap().entrySet();
            for(Map.Entry<K, V> entry : entrySet) {
                if(entry.getValue() == value) {
                    remove(entry.getKey());
                    hasValue = true;
                }
            }
            return hasValue;
        }
        
        @Override
        public long size() {
            return loadingCache.size();
        }
    }
}

class MapCacheBuilder<K, V> extends CacheBuilder<K, V> {

    int initialCapacity = 16;

    @Deprecated
    @Override
    public CacheBuilder<K, V> maxinumSize(Long size) {
        return this;
    }

    @Override
    public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        return this;

    }

    @Override
    public LocalCache<K, V> build() {

        return new MapLocalCache<K, V>().build(this);

    }
    
    

    static class MapLocalCache<K, V> implements LocalCache<K, V> {

        ConcurrentHashMap<K, V> map = null;

        /**
         * key 或者 vlaue 为空时，抛出空指针错误
         */
        @Override
        public void put(K key, V value) {
            map.put(key, value);
        }

        @Override
        public V get(K key) {
            return map.get(key);
        }

        public MapLocalCache<K, V> build(MapCacheBuilder<K, V> builder) {
            map = new ConcurrentHashMap<K, V>(builder.initialCapacity);
            return this;
        }
        
        @Override
        public boolean contains(K key) {
            return map.containsKey(key);
        }
        
        @Override
        public void remove(K key) {
            map.remove(key);
        }
        
        @Override
        public boolean removeValue(V value) {
            boolean hasValue = false;
            Set<Map.Entry<K, V>> entrySet = map.entrySet();
            for(Map.Entry<K, V> entry : entrySet) {
                if(entry.getValue() == value) {
                    remove(entry.getKey());
                    hasValue = true;
                }
            }
            return hasValue;
        }
        
        @Override
        public long size() {
            return map.size();
        }
    }

}
