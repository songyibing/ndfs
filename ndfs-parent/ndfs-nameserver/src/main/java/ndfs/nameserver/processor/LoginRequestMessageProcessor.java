
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.processor;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.ChannelCache;
import ndfs.core.common.message.LoginRequestMessage;
import ndfs.core.common.message.LoginResponseMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;
import ndfs.nameserver.cache.BlockManager;
import ndfs.nameserver.cache.ServerManager;

/**
 * <p>
 * Description: 登录的请求处理器
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午1:27:03
 */
@Processor(msgType = MsgTypeEnum.LOGIN_REQUEST)
public class LoginRequestMessageProcessor extends RequestMessageProcessor<LoginRequestMessage, LoginResponseMessage> {
    private static final Logger logger = LogUtils.getLogger(LoginRequestMessageProcessor.class);

    @Override
    protected LoginResponseMessage process(ChannelHandlerContext channelHandlerContext,
            LoginRequestMessage requestMessage) {
        ChannelCache.addChannel(requestMessage.getChannelId(),
                (SocketChannel) channelHandlerContext.channel());
        BlockManager.LoadHeartBeatInfo(requestMessage.getBlockInfoList());
        InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
        String ip = inetSocketAddress.getAddress().getHostAddress();
        
        ServerManager.loadServerInfo(requestMessage, ip);
        BlockManager.list();
        ServerManager.list();
        logger.info("IP为%s的data server登录成功", ip);
        return new LoginResponseMessage();

    }

}
