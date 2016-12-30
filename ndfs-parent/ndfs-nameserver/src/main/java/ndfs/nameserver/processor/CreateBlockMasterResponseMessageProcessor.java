
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.processor;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.common.message.CreateBlockMasterResultRequestMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ResponseMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;
import ndfs.nameserver.cache.BlockManager;
import ndfs.nameserver.core.Global;
import ndfs.nameserver.core.Statistics;

/**
 * <p>
 * Description: 【添加块】操作结果的消息处理器
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月28日 上午11:20:00
 */
@Processor(msgType = MsgTypeEnum.CREATE_BLOCK_RESULT_MASTER_REQUEST)
public class CreateBlockMasterResponseMessageProcessor
        extends RequestMessageProcessor<CreateBlockMasterResultRequestMessage, ResponseMessage> {
    private static final Logger logger = LogUtils.getLogger(CreateBlockMasterResponseMessageProcessor.class);

    @Override
    protected ResponseMessage process(ChannelHandlerContext channelHandlerContext,
            CreateBlockMasterResultRequestMessage requestMessage) {

        if (requestMessage.isSuccess()) {
            long oldMaxBlockNumber = requestMessage.getBlockNumberMin();
            long newMaxBlockNumber = oldMaxBlockNumber + requestMessage.getBlockCount() - 1;
            Statistics.casCurrentMaxBlockNumber(oldMaxBlockNumber - 1, newMaxBlockNumber);
            Global.creatingBlockLock.compareAndSet(true, false);
            for (int i = 0; i < requestMessage.getBlockCount(); i++) {
                BlockManager.addNewBlock(oldMaxBlockNumber + i, requestMessage.getDataserverList());
            }
            logger.info("创建块成功，块id最大值由%s更改为%s", oldMaxBlockNumber, newMaxBlockNumber);
        } else {
            logger.info("创建块失败");
            Global.creatingBlockLock.compareAndSet(true, false);
        }
        return ResponseMessage.nullMessage();

    }

}
