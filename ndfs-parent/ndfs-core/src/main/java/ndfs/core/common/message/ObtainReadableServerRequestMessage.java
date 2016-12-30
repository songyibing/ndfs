
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;
/**
 * <p>
 *     Description: 根据block id,获取一个可读的server
 * </p>
 * @author yibingsong
 * @Date 2016年8月1日 下午4:55:48
 */
public class ObtainReadableServerRequestMessage extends RequestMessage{
    public long getBlockId() {
    
        return blockId;
    }

    public void setBlockId(long blockId) {
    
        this.blockId = blockId;
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.msgType = MsgTypeEnum.OBTAIN_READABLE_SERVER_REQUEST.getCode();
    }
    
    public long blockId;
    
    public int fileId;

    public int getFileId() {
    
        return fileId;
    }

    public void setFileId(int fileId) {
    
        this.fileId = fileId;
    }
    
}

    