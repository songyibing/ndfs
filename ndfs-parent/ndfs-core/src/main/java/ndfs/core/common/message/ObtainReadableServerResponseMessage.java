
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

/**
 * <p>
 * Description:读取文件时，返回给client的server信息
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月2日 上午9:44:32
 */
public class ObtainReadableServerResponseMessage extends ResponseMessage {

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.OBTAIN_READABLE_SERVER_RESPONSE.getCode();
    }

    public String getIp() {

        return ip;

    }

    public void setIp(String ip) {

        this.ip = ip;

    }

    public int getFileId() {
        
        return fileId;
            
    }

    public void setFileId(int fileId) {
        
        this.fileId = fileId;
            
    }

    public long getBlockId() {
        
        return blockId;
            
    }

    public void setBlockId(long blockId) {
        
        this.blockId = blockId;
            
    }

    private String ip;
    
    private int fileId;
    
    private long blockId;

}
