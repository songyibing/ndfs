
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package org.ndfs.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import junit.framework.TestCase;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月5日 下午3:52:30
 */
public class GuavaTest extends TestCase {
    public void testCache() {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, String>() {

            @Override
            public String load(Integer key) throws Exception {

                // TODO Auto-generated method stub
                return null;

            }

        });
        cache.put(1, "");
    }

}
