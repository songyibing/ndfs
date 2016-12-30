
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

/**
 * <p>
 * Description: master向slave发送创建块的请求
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月27日 下午5:01:01
 */
public class CreateBlockToSlaveRequest extends RequestMessage {
    private long blockCount;

    private long blockNumberMin;
    
    private long sessionId;

    public long getBlockCount() {
    
        return blockCount;
    }

    public void setBlockCount(long blockCount) {
    
        this.blockCount = blockCount;
    }

    public long getBlockNumberMin() {
    
        return blockNumberMin;
    }

    public void setBlockNumberMin(long blockNumberMin) {
    
        this.blockNumberMin = blockNumberMin;
    }

    public CreateBlockToSlaveRequest(long blockCount, long blockNumberMin, long sessionId) {
        super();
        this.blockCount = blockCount;
        this.blockNumberMin = blockNumberMin;
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        
        return sessionId;
            
    }

    public void setSessionId(long sessionId) {
        
        this.sessionId = sessionId;
            
    }
}
