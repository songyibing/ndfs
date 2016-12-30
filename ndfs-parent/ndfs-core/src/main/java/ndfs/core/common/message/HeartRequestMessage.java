
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;
/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 下午8:06:11
 */
public class HeartRequestMessage extends RequestMessage{

    private static final long serialVersionUID = 1L;

    // 可分配空间
    private long FreeBlockCount;
    
    public HeartRequestMessage() {
        this.msgType = MsgTypeEnum.HEARTBEAT_REQUST.getCode();
    }

    public long getFreeBlockCount() {
        
        return FreeBlockCount;
            
    }

    public void setFreeBlockCount(long freeBlockCount) {
        
        FreeBlockCount = freeBlockCount;
            
    }
    
  

    
}

    