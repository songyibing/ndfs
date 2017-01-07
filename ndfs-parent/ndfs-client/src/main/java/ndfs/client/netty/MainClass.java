
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.netty;

import static ndfs.core.common.BootLoader.getProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.UUID;

import ndfs.client.cache.UuidContent;
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
    
    public static void main(String[] args) throws InterruptedException, IOException {
        String url = null;
        for(int i=1; i<=8; i++) {
            url = upload("D:\\" + i + ".png");
            System.out.println(url);
        }
        
        read("6|4");
    }
    
    public static String upload(String path) throws InterruptedException {
     // 上传一个文件
        String result = null;
        String uuid = UUID.randomUUID().toString();
        NettyClientBootstrap dataClient = new NettyClientBootstrap(getProperties(keyForNameserverIp), getProperties(keyForNameserverPort), new NettyClientHandler(), "client 的 netty client 启动成功");
        dataClient.getSocketChannel().writeAndFlush(new ObtainWritableBlockRequestMessage(path, uuid));
        // 可以考虑使用CountDownLatch
        while(true) {
            result = UuidContent.getInstance().get(uuid);
            if(result != null) {
                break;
            }
        }
        dataClient.getSocketChannel().closeFuture().sync();
        return result;
        
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

class Mythread extends Thread {
    RandomAccessFile br;
    public Mythread(RandomAccessFile br) {
           this.br = br;
        // TODO Auto-generated constructor stub

    }
    public void run() {
        try {
            System.out.println(br.readLine());
            br.close();
        } catch (IOException e) {
            
            // TODO Auto-generated catch block
            e.printStackTrace();
                
        }
    }
}

    