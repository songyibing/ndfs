
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.processor;


import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.LocalCache;
import ndfs.core.common.message.HeartResponseMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;
import ndfs.dataserver.UsedBlockInfoCache;
import ndfs.dataserver.WritableBlockInfoCache;
import ndfs.dataserver.model.BlockInfo;
/**
 * <p>
 * Description: 心跳回应处理器
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午12:59:52
 */
@Processor(msgType = MsgTypeEnum.HEARTBEAT_RESPONSE)
public class HeartResponseProcessor extends ResponseMessageProcessor<HeartResponseMessage, RequestMessage> {

    private static final Logger logger = LogUtils.getLogger(HeartResponseProcessor.class);

    private static LocalCache<Long, BlockInfo> usedBlockInfoCache = UsedBlockInfoCache.INSTANCE;
    private static LocalCache<Long, BlockInfo> writableBlockInfoCache = WritableBlockInfoCache.INSTANCE;
    
    @Override
    protected RequestMessage process(ChannelHandlerContext channelHandlerContext,
            HeartResponseMessage responseMessage) {
        logger.info("接收到 name server 的心跳回应消息");
        
        if (responseMessage.getOperationType() == HeartResponseMessage.OperationType.CREATE_BLOCK) {
            
        }
        
        return RequestMessage.nullObject();
    }
}
