
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.NettyServerBootstrap;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.message.CommonMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 上午11:43:42
 */
public class NettyClientBootstrap {
    private int port;
    private String host;
    private String logInfo;
    private SocketChannel socketChannel;
    private SimpleChannelInboundHandler<CommonMessage> handler;
    private static final Logger logger = LogUtils.getLogger(NettyServerBootstrap.class);
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);
    
    public SocketChannel getSocketChannel() {
        if(socketChannel == null) throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
        return socketChannel;
    }

    public NettyClientBootstrap(String host, String port, SimpleChannelInboundHandler<CommonMessage> handler, String logInfo) {
        this.port = Integer.valueOf(port);
        this.host = host;
        this.logInfo = logInfo;
        this.handler = handler;
        try {
            start();
        } catch (InterruptedException e) {
            throw new CommonException(StatusCodeEnum.THREAD_INTERRUPT_ERROR);
        }
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(eventLoopGroup);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                socketChannel.pipeline().addLast(handler.getClass().newInstance());
            }
        });
        ChannelFuture future = bootstrap.connect().sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            logger.info(logInfo);
        }
      
    }
}
