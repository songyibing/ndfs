
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.model;

/**
 * <p>
 *     Description: 用于心跳消息的block 信息
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 上午11:00:23
 */
public class BlockInfoInHeartBeat {
    
    //块的唯一id
    private long id;
    
    //实际大小
    private long actualSize;

    public Long getId() {
    
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

}

    