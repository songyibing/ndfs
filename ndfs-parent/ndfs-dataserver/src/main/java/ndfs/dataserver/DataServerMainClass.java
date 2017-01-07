
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import static ndfs.core.common.BootLoader.getProperties;
import static ndfs.core.common.Unit.MB;

import java.io.File;
import java.util.concurrent.TimeUnit;

import ndfs.core.common.BootLoader;
import ndfs.core.common.NettyClientBootstrap;
import ndfs.core.common.NettyServerBootstrap;
import ndfs.core.common.message.HeartRequestMessage;
import ndfs.core.common.message.LoginRequestMessage;
import ndfs.core.utils.FileUtils;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;
import ndfs.data.netty.client.NettyClientHandler;
import ndfs.data.netty.server.NettyServerHandler;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月26日 下午1:24:56
 */
public class DataServerMainClass {
    private static final String keyForDataserverPort = "dataserver.port";
    private static final String keyForNameserverIp = "nameserver.ip";
    private static final String keyForNameserverPort = "nameserver.port";
    private static final String keyForBlockSize = "block.size";
    private static final String partitionPath = "D://";
    private static final Logger logger = LogUtils.getLogger(DataServerMainClass.class);

    public static void main(String[] args) throws InterruptedException {
        // 创建block
        initBlock();
        // 启动服务
        startServer();
        // 连接name server
        NettyClientBootstrap dataClient = new NettyClientBootstrap(getProperties(keyForNameserverIp),
                getProperties(keyForNameserverPort), new NettyClientHandler(), "data server的netty client 启动成功");

        LoginRequestMessage message = new LoginRequestMessage();
        message.setBlockInfoList(BlockInfoLoader.readTotalBlockInfo());
        message.setFreeBlockCount(Global.freeBlockCount);
        dataClient.getSocketChannel().writeAndFlush(message);
        while (true) {
            TimeUnit.SECONDS.sleep(10);
            HeartRequestMessage heartRequestMessage = new HeartRequestMessage();
            dataClient.getSocketChannel().writeAndFlush(heartRequestMessage);
        }

    }

    private static void initBlock() {
        logger.info("准备创建Block");
        Long start = System.currentTimeMillis();
        // for (long begin = responseMessage.getBlockNumberMin(); begin <=
        // responseMessage
        // .getBlockNumberMax(); begin++) {
        // FileUtils.createFile("D://Block." + begin,
        // responseMessage.getBlockSize());
        // BlockInfo blockInfo = new BlockInfo();
        // blockInfo.init(begin, "D://Block." + begin);
        // totalBlockInfoCache.put(begin, blockInfo);
        // writableBlockInfoCache.put(begin, blockInfo);
        // }
        System.out.println(System.currentTimeMillis());
        File file = new File("D://");
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        System.out.println(System.currentTimeMillis());
        long allocateSpace = 0;
        if ((double) freeSpace / totalSpace > 0.6) {
            allocateSpace = freeSpace - (long) (totalSpace * 0.6);
        }
        long count = allocateSpace / (Long.valueOf(BootLoader.getProperties(keyForBlockSize)) * MB);
        for (int i = 0; i < count; i++) {
            FileUtils.createFile("D://B." + i, Long.valueOf(BootLoader.getProperties(keyForBlockSize)) * MB);
            TotalBlockInfoCache.addBlock("D://B." + i);
            logger.info("创建文件D://B." + i);
        }
        Global.freeBlockCount = count;
        Long over = System.currentTimeMillis();
        logger.info("创建文件共花费" + (over - start) + "ms");

    }

    private static void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyServerBootstrap dataServer = new NettyServerBootstrap(getProperties(keyForDataserverPort),
                        new NettyServerHandler(), "data server的 netty server启动成功");
            }
        }).start();
    }
}
