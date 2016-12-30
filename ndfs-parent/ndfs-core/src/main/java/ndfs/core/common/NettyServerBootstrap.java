
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.message.CommonMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 上午11:41:22
 */
public class NettyServerBootstrap {
    private int port;
    private String logInfo;
    private SimpleChannelInboundHandler<CommonMessage> handler;
    private static final Logger logger = LogUtils.getLogger(NettyServerBootstrap.class);
    public NettyServerBootstrap(String port, SimpleChannelInboundHandler<CommonMessage> handler, String logInfo) {
        this.port = Integer.valueOf(port);
        this.handler = handler;
        this.logInfo = logInfo;
        try {
            bind();
        } catch (InterruptedException e) {
            throw new CommonException(StatusCodeEnum.THREAD_INTERRUPT_ERROR);
        }
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        // 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 保持长连接状态
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                p.addLast(new ObjectEncoder());
                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast(handler.getClass().newInstance());
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        f.channel().closeFuture().sync();
        if (f.isSuccess()) {
            logger.info(logInfo);
        }
    }

}