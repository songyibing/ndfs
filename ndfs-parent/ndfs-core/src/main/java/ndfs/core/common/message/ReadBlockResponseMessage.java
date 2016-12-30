
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import ndfs.core.utils.LogUtils;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月2日 上午10:24:33
 */
public class ReadBlockResponseMessage extends ResponseMessage {

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.READ_BLOCK_RESPONSE.getCode();
    }
    
    public byte[] getContent() {
        
        return content;
            
    }

    public void setContent(byte[] content) {
        
        this.content = content;
            
    }

    private byte[] content;
    
    /**
     * content内容过多，避免日志打印。参考 {@link LogUtils}的isDeclared方法
     */
    public String toString() {
        return "ReadBlockRequestMessage[msgType=" + msgType;
    }

}
