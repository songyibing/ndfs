
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.processor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.ChannelCache;
import ndfs.core.common.BootLoader;
import ndfs.core.common.message.HeartRequestMessage;
import ndfs.core.common.message.HeartResponseMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午1:27:03
 */
@Processor(msgType = MsgTypeEnum.HEARTBEAT_REQUST)
public class HeartRequestMessageProcessor extends RequestMessageProcessor<HeartRequestMessage, HeartResponseMessage> {
    private static final Logger logger = LogUtils.getLogger(HeartRequestMessageProcessor.class);
    private static final String keyForBlockSize = "block.size";
    private static final int KB = 1024;
    private static final int MB = KB * KB;
    // 单位为字节
    private static final long blockSize = Long.valueOf(BootLoader.getProperties(keyForBlockSize)) * MB;

    @Override
    protected HeartResponseMessage process(ChannelHandlerContext channelHandlerContext,
            HeartRequestMessage requestMessage) {
        logger.info("接收到心跳数据");
        //从缓存中获取连接
        SocketChannel socketChannel = ChannelCache.getChannel(requestMessage.getChannelId());
        
        
        HeartResponseMessage heartResponseMessage = new HeartResponseMessage();
        
       // logger.info("准备建立文件块，块范围是%s-%s", heartResponseMessage.getBlockNumberMin(), heartResponseMessage.getBlockNumberMax());
        //TODO 获取可创建block的数量
//        CreateBlockRequestMessage createBlockRequestMessage = new CreateBlockRequestMessage(Long.valueOf(BootLoader.getProperties(keyForBlockSize)), 
//                Long.valueOf(BootLoader.getProperties(keyForBlockSize)), heartResponseMessage.getBlockNumberMin(), heartResponseMessage.getBlockNumberMax());
        //TODO 找到一个可用的data server作为master
        return heartResponseMessage;

    }

}
