
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

/**
 * <p>
 * Description: client上传文件时，name server返回的block id消息
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月26日 下午2:39:05
 */
public class ObtainWritableBlockRequestMessage extends RequestMessage {

    public ObtainWritableBlockRequestMessage(String path) {
        super();
        this.path = path;
    }

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.OBTAIN_WRITABLE_BLOCK_REQUEST.getCode();
    }

    public String getPath() {

        return path;

    }

    public void setPath(String path) {

        this.path = path;

    }

    private String path;

}
