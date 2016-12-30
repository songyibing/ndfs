
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;


import ndfs.core.cache.CacheBuilder;
import ndfs.core.cache.LocalCache;
import ndfs.dataserver.model.BlockInfo;

/**
 * <p>
 *     Description: 所有使用到的Block块，包括写满的块，未写满的块
 * </p>
 * @author yibingsong
 * @Date 2016年7月25日 下午5:26:46
 */
public class UsedBlockInfoCache {
    
    public static final LocalCache<Long, BlockInfo> INSTANCE = getInstance();
    
    private static LocalCache<Long, BlockInfo> getInstance() {
        return CacheBuilder.newBuilder
                (CacheBuilder.GUAVA_CACHE_BUILDER_TYPE, Long.class, BlockInfo.class)
                .initialCapacity(800).build();
    }
    
    public boolean isEmpty() {
        return INSTANCE.size() == 0;
    }
    
    public static void addBlockInfo(BlockInfo blockInfo) {
        INSTANCE.put(blockInfo.getId(), blockInfo);
    }
    
    public static BlockInfo getBlockInfo(long blockId) {
        return INSTANCE.get(blockId);
    }
    


}

    