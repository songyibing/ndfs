
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.processor;

import io.netty.channel.ChannelHandlerContext;
import ndfs.client.cache.UuidContent;
import ndfs.core.annotation.Processor;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.common.message.WriteBlockResponseMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月1日 下午3:02:02
 */
@Processor(msgType = MsgTypeEnum.WRITE_BLOCK_RESPONSE)
public class WriteBlockResponseProcessor extends ResponseMessageProcessor<WriteBlockResponseMessage, RequestMessage> {

    private static final Logger logger = LogUtils.getLogger(WriteBlockResponseProcessor.class);
    
    @Override
    protected RequestMessage process(ChannelHandlerContext channelHandlerContext,
            WriteBlockResponseMessage responseMessage) {
        logger.info("上传文件成功，文件url为" + responseMessage.getFilePath());
        String uuid = responseMessage.getUuid();
        UuidContent.getInstance().put(uuid, responseMessage.getFilePath());
        return RequestMessage.nullObject();

    }

}
