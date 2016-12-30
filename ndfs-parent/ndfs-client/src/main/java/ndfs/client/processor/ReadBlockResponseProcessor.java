
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.processor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.NettyServerBootstrap;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ReadBlockResponseMessage;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月2日 上午10:46:50
 */
@Processor(msgType = MsgTypeEnum.READ_BLOCK_RESPONSE)
public class ReadBlockResponseProcessor extends ResponseMessageProcessor<ReadBlockResponseMessage, RequestMessage> {
    private static final Logger logger = LogUtils.getLogger(NettyServerBootstrap.class);
    @Override
    protected RequestMessage process(ChannelHandlerContext channelHandlerContext,
            ReadBlockResponseMessage responseMessage) {
        byte[] content = responseMessage.getContent();
        try {
            FileOutputStream fis = new FileOutputStream(new File("D:\\new.jpg"));
            BufferedOutputStream bos = new BufferedOutputStream(fis);
            bos.write(content);
            bos.flush();
            bos.close();
            logger.info("读取的文件被存放在D:\\new.jpg中");
            
        } catch (Exception e) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR, e.getMessage());
        }
        return RequestMessage.nullObject();

    }

}
