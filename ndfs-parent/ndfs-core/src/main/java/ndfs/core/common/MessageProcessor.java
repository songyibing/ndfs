
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.common.message.CommonMessage;

/**
 * <p>
 *     Description: 消息吹接口
 * </p>
 * @author yibingsong
 * @Date 2016年7月20日 下午1:16:06
 */
public interface MessageProcessor<S extends CommonMessage> {
    public void processMessage(ChannelHandlerContext channelHandlerContext, S message);
}

    