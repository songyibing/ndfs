
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import ndfs.core.utils.LogUtils;

/**
 * <p>
 * Description:向block中写文件的请求消息
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月1日 下午12:39:26
 */
public class WriteBlockRequestMessage extends RequestMessage {
    private byte[] fileContent;

    private long blockId;

    public void setFileContent(byte[] fileContent) {

        this.fileContent = fileContent;
    }

    public byte[] getFileContent() {

        return fileContent;
    }

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.WRITE_BLOCK_REQUEST.getCode();
    }

    public long getBlockId() {

        return blockId;

    }

    /**
     * content内容过多，避免日志打印。参考 {@link LogUtils}的isDeclared方法
     */
    public String toString() {
        return "WriteBlockRequestMessage[blockId=" + blockId + "]";
    }

    public void setBlockId(long blockId) {

        this.blockId = blockId;

    }
}
