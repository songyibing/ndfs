
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import com.google.common.base.Strings;

import io.netty.channel.ChannelId;
import ndfs.core.common.BootLoader;

/**
 * <p>
 * Description: 把block server 发送给 name server 的消息称为request message。 包括标识block
 * server的{@link ChannelId} 字段
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 下午7:51:21
 */
public class RequestMessage extends CommonMessage {
    
    private static final long serialVersionUID = 1L;
    
    private static final RequestMessage NULL_REQUEST_MESSAGE = new RequestMessage();
    
    private static final String keyForDataserverId = "dataserver.id";
    public static RequestMessage nullObject() {
        return NULL_REQUEST_MESSAGE;
    }
    
    
    private int channelId;

    public int getChannelId() {
        return channelId;
    }

    @Override
    protected void initialize() {
        int code = 0;
        if(!Strings.isNullOrEmpty(BootLoader.getProperties(keyForDataserverId))) {
            code = Integer.valueOf(BootLoader.getProperties(keyForDataserverId));
        }
        channelId = code;
    }
    
    public RequestMessage() {
        super();
    }
    
    


}
