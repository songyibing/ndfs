
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.processor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import ndfs.client.netty.NettyClientBootstrap;
import ndfs.client.netty.NettyClientHandler;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ObtainWritableBlockResponseMessage;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.common.message.WriteBlockRequestMessage;

/**
 * <p>
 * Description: 处理从name server获取的block id消息。向data server写入文件
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月26日 下午2:35:24
 */
@Processor(msgType = MsgTypeEnum.OBTAIN_WRITABLE_BLOCK_RESPONSE)
public class ObtainWritableBlockResponseProcessor extends ResponseMessageProcessor<ObtainWritableBlockResponseMessage, RequestMessage> {

    @Override
    protected RequestMessage process(ChannelHandlerContext channelHandlerContext, ObtainWritableBlockResponseMessage message) {
        NettyClientBootstrap dataClient = new NettyClientBootstrap(message.getServerIp(), "9998",
                new NettyClientHandler(), "client 的 netty client 启动成功");
        WriteBlockRequestMessage writeBlockRequestMessage = new WriteBlockRequestMessage();
        writeBlockRequestMessage.setFileContent(getBytesAndLength(message.getPath()));
        writeBlockRequestMessage.setBlockId(message.getBlockId());
        writeBlockRequestMessage.setUuid(message.getUuid());
        // 写入文件
        dataClient.getSocketChannel().writeAndFlush(writeBlockRequestMessage);
        try {
            dataClient.getSocketChannel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR, e.getMessage());
        }
        return RequestMessage.nullObject();
  
    }

    private byte[] getBytesAndLength(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
