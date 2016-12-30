
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.ToString;

import static ndfs.core.common.StatusCodeEnum.*;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 上午11:00:23
 */
public class BlockInfo extends ToString{
    
    public static BlockInfo newBlock(long id,   List<Integer> dataserverList) {
        BlockInfo blockInfo = new BlockInfo();
        blockInfo.setActualSize(0);
        blockInfo.setFull(false);
        blockInfo.setId(id);
        blockInfo.setDataserverId(dataserverList);
        blockInfo.setWritingBlockLock(new AtomicBoolean(false));
        return blockInfo;
    }
    
    //块的唯一id
    private long id;
    
    //实际大小
    private long actualSize;
    
    //是否写满
    private boolean isFull;
    
    //当前块是否正在写入
    private transient AtomicBoolean writingBlockLock;
    
    // 存放该block的dataserver id，第一个为master
    private List<Integer> dataserverId = new ArrayList<Integer>();
    
    public long getId() {
    
        return id;
    }

    public void setId(long id) {
    
        this.id = id;
    }

    public long getActualSize() {
    
        return actualSize;
    }

    public void setActualSize(long actualSize) {
    
        this.actualSize = actualSize;
    }


    public boolean isFull() {
    
        return isFull;
    }

    public void setFull(boolean isFull) {
    
        this.isFull = isFull;
    }

    public List<Integer> getDataserverId() {
        
        return dataserverId;
            
    }

    public void setDataserverId(List<Integer> dataserverId) {
        
        this.dataserverId = dataserverId;
            
    }
    
    public int getMasterServerId() {
        return dataserverId.get(0);
    }

    public AtomicBoolean getWritingBlockLock() {
    
        return writingBlockLock;
    }

    public void setWritingBlockLock(AtomicBoolean writingBlockLock) {
    
        this.writingBlockLock = writingBlockLock;
    }
    
    
    
}

    