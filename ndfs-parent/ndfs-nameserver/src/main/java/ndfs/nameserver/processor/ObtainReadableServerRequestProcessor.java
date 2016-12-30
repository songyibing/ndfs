
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.processor;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ObtainReadableServerRequestMessage;
import ndfs.core.common.message.ObtainReadableServerResponseMessage;
import ndfs.nameserver.cache.BlockManager;

/**
 * <p>
 * Description: 处理[<b>获取可读server请求<b>]
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月2日 上午9:43:54
 */
@Processor(msgType = MsgTypeEnum.OBTAIN_READABLE_SERVER_REQUEST)
public class ObtainReadableServerRequestProcessor
        extends RequestMessageProcessor<ObtainReadableServerRequestMessage, ObtainReadableServerResponseMessage> {

    @Override
    protected ObtainReadableServerResponseMessage process(ChannelHandlerContext channelHandlerContext,
            ObtainReadableServerRequestMessage requestMessage) {
        long blockId = requestMessage.getBlockId();
        String ip = BlockManager.findReadableBlock(blockId);
        ObtainReadableServerResponseMessage responseMessage = new ObtainReadableServerResponseMessage();
        responseMessage.setIp(ip);
        responseMessage.setFileId(requestMessage.getFileId());
        responseMessage.setBlockId(requestMessage.getBlockId());
        return responseMessage;
    }

}
