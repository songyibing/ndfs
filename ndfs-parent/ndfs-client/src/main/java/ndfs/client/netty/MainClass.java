
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.netty;

import static ndfs.core.common.BootLoader.getProperties;

import ndfs.core.common.message.ObtainReadableServerRequestMessage;
import ndfs.core.common.message.ObtainWritableBlockRequestMessage;
/**
 * <p>
 *     Description: 先清除D盘下的block文件，再运行name server，再运行block server，最后运行该函数的main方法，即可上传文件。
 * </p>
 * @author yibingsong
 * @Date 2016年7月26日 下午1:56:43
 */
public class MainClass {
    private static final String keyForNameserverIp = "nameserver.ip";
    private static final String keyForNameserverPort = "nameserver.port";
    
    public static void main(String[] args) throws InterruptedException {
//        for(int i=1; i<=8; i++) {
//            upload("D:\\" + i + ".png");
//        }
        read("6|1");
    }
    
    public static void upload(String path) throws InterruptedException {
     // 上传一个文件
        NettyClientBootstrap dataClient = new NettyClientBootstrap(getProperties(keyForNameserverIp), getProperties(keyForNameserverPort), new NettyClientHandler(), "client 的 netty client 启动成功");
        dataClient.getSocketChannel().writeAndFlush(new ObtainWritableBlockRequestMessage(path));
        dataClient.getSocketChannel().closeFuture().sync();
    }
    
    public static void read(String path) throws InterruptedException {
        NettyClientBootstrap dataClient = new NettyClientBootstrap(getProperties(keyForNameserverIp), getProperties(keyForNameserverPort), new NettyClientHandler(), "client 的 netty client 启动成功");
        ObtainReadableServerRequestMessage message = new ObtainReadableServerRequestMessage();
        String name[] = path.split("\\|");
        long blockId = Long.valueOf(name[0]);
        int fileId = Integer.valueOf(name[1]);
        message.setBlockId(blockId);
        message.setFileId(fileId);
        dataClient.getSocketChannel().writeAndFlush(message);
        dataClient.getSocketChannel().closeFuture().sync();
    }
}

    