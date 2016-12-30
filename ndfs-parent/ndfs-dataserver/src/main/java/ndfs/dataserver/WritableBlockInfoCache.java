
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import ndfs.core.cache.CacheBuilder;
import ndfs.core.cache.LocalCache;
import ndfs.dataserver.model.BlockInfo;

/**
 * <p>
 *     Description: 所有尚未写满的block
 * </p>
 * @author yibingsong
 * @Date 2016年7月25日 下午5:28:45
 */
public class WritableBlockInfoCache {
public static final LocalCache<Long, BlockInfo> INSTANCE = getInstance();
    
    private static LocalCache<Long, BlockInfo> getInstance() {
        return CacheBuilder.newBuilder
                (CacheBuilder.DEFAULT_CACHE_BUILDER_TYPE, Long.class, BlockInfo.class)
                .initialCapacity(800).build();
    }
    
    public static void addBlockInfo(BlockInfo blockInfo) {
        INSTANCE.put(blockInfo.getId(), blockInfo);
    }
}

    