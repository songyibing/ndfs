
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;
/**
 * <p>
 *     Description: data server发送的文件写入结果的消息
 * </p>
 * @author yibingsong
 * @Date 2016年8月1日 下午2:40:37
 */
public class WriteBlockResponseMessage extends ResponseMessage{

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.WRITE_BLOCK_RESPONSE.getCode();
    }
    
    public String getFilePath() {
        
        return filePath;
            
    }

    public void setFilePath(String filePath) {
        
        this.filePath = filePath;
            
    }

    private String filePath;
    

}

    