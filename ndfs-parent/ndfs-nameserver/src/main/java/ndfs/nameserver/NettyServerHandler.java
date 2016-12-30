
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import ndfs.core.cache.ChannelCache;
import ndfs.core.common.BootLoader;
import ndfs.core.common.MessageProcessor;
import ndfs.core.common.message.CommonMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 上午11:31:57
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<CommonMessage> {
    private static final Logger logger = LogUtils.getLogger(NettyServerHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // channel失效，从Map中移除
        ChannelCache.removeSocketChannel((SocketChannel) ctx.channel());
    }

   
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, CommonMessage message)
            throws Exception {

        MessageProcessor processor = BootLoader.getProcessor(message.getMsgType());
        processor.processMessage(channelHandlerContext, message);
        ReferenceCountUtil.release(message);

    }
}
