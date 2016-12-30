
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ndfs.core.cache.ChannelCache;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.BootLoader;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.model.BlockInfoInHeartBeat;
import ndfs.nameserver.core.BlockInfo;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月27日 上午10:06:02
 */
public class BlockManager {
    private static Map<Long, BlockInfo> totalBlockInfo = new ConcurrentHashMap<Long, BlockInfo>();
    
    private static Map<Long, BlockInfo> writableBlockInfo = new ConcurrentHashMap<Long, BlockInfo>();
    private static final String keyForMinWritableBlockCount = "min.writableBlock.count";
    private static final int minWritableBlockCount = getMinWritableBlockCount();
    
    /**
     * 
    * @Title: getMinWritableBlockCount 
    * @Description:  获取属性min.writableBlock.count的值。当前可写块总数小于该值时，则创建块。
    * @param @return  
    * @return int
    * @throws
     */
    private static final int getMinWritableBlockCount() {
        return Integer.valueOf(BootLoader.getProperties(keyForMinWritableBlockCount));
    }
    public static void LoadHeartBeatInfo(List<BlockInfoInHeartBeat> blockInfoInHeartBeatList) {
        //TODO
        return;
    }
    
    public static void list() {
        System.out.println(totalBlockInfo.toString());
    }
    
    /**
     * 
    * @Title: needCreateBlock 
    * @Description:  判断是否是要创建块
    * @return  
    * @return boolean
    * @throws
     */
    public static boolean needCreateBlock() {
        if(ServerManager.size() == 0) return false;
        return writableBlockInfo.size() < minWritableBlockCount;
    }
    
    /**
     * 
    * @Title: addNewBlock 
    * @Description:  块创建完毕后，更新内存
    * @param blockId
    * @param dataserverList  
    * @return void
    * @throws
     */
    public static void addNewBlock(long blockId, List<Integer> dataserverList) {
        BlockInfo blockInfo = BlockInfo.newBlock(blockId, dataserverList);
        //先放到total里面，再放到writable里面，防止出错
        totalBlockInfo.put(blockInfo.getId(), blockInfo);
        writableBlockInfo.put(blockInfo.getId(), blockInfo);
    }
    
    /**
     * 
    * @Title: findWritableBlock 
    * @Description:  寻找一个可写块。【遍历太慢，需要用其他方式重新实现】
    * @return BlockInfo
    * @throws
     */
    @SuppressWarnings("unused")
    public static BlockInfo findWritableBlock() {
        Set<Long> set = writableBlockInfo.keySet();
        Random random = new Random(System.currentTimeMillis());
        int index = (int) random.nextInt(set.size());
        int i=0;
        BlockInfo blockInfo = null;
        for(Long blockId : set) {
            if(i==index) {
                return writableBlockInfo.get(blockId);
            }
            i++;
        }
        if(blockInfo == null) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
        }
        while(tryWriteBlock(blockInfo) == false) {
            blockInfo = findWritableBlock();
        }
        return blockInfo;
    }
    
    
    public static String findReadableBlock(long blockId) {
       BlockInfo blockInfo = writableBlockInfo.get(blockId);
       if(blockInfo == null) {
           throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR, "寻找可读块时出现错误");
       }
       List<Integer> serverIdList = blockInfo.getDataserverId();
       if(serverIdList == null || serverIdList.size() == 0) {
           throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR, "寻找可读块时出现错误");
       }
       int index = new Random().nextInt(serverIdList.size());
       int serverId = serverIdList.get(index);
       return ServerManager.getServerIp(serverId);
    }
    /**
     * 
    * @Title: tryWriteBlock 
    * @Description:  尝试给块加写锁
    * @param blockInfo
    * @return 加锁成功，返回true，否则返回false
    * @throws
     */
   public static boolean tryWriteBlock(BlockInfo blockInfo) {
       if(blockInfo.getWritingBlockLock().get() == false) {
           return blockInfo.getWritingBlockLock().compareAndSet(false, true);
       }
       return false;
   }
    
    
}

    