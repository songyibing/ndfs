package org.ndfs.core;



import com.google.common.cache.Cache;

import junit.framework.TestCase;
import ndfs.core.cache.CacheBuilder;
import ndfs.core.cache.LocalCache;

/**
 * <p>
 *     Description: 测试缓存的两种实现：concurrentHashMap和google guava
 * </p>
 * @author yibingsong
 * @Date 2016年7月21日 下午12:42:28
 */
public class CacheTest extends TestCase {
    public void testCache() {
        LocalCache<Integer, String> guavaCache = CacheBuilder.newBuilder
                (CacheBuilder.GUAVA_CACHE_BUILDER_TYPE, Integer.class, String.class)
                .initialCapacity(100).maxinumSize(500L).build();
        guavaCache.put(5, "hello");
        
        assertEquals("hello", guavaCache.get(5)); 
        
        LocalCache<Integer, String> mapCache = CacheBuilder.newBuilder
                (CacheBuilder.MAP_CACHE_BUILDER_TYPE, Integer.class, String.class)
                .initialCapacity(100).maxinumSize(200L).build();
        mapCache.put(4, "hello");
        assertEquals("hello", mapCache.get(4));
    }
}
