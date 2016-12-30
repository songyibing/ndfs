
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import ndfs.core.utils.LogUtils;

/**
 * <p>
 *     Description: 从block读文件的请求消息
 * </p>
 * @author yibingsong
 * @Date 2016年8月2日 上午10:18:13
 */
public class ReadBlockRequestMessage extends RequestMessage{
    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.READ_BLOCK_REQUEST.getCode();
    }
    
    public long getBlockId() {
        
        return blockId;
            
    }

    public void setBlockId(long blockId) {
        
        this.blockId = blockId;
            
    }

    public int getFileId() {
        
        return fileId;
            
    }

    public void setFileId(int fileId) {
        
        this.fileId = fileId;
            
    }

    
    
    private long blockId;
    
    private int fileId;
}

    