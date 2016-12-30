
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import ndfs.core.annotation.Processor;
import ndfs.core.common.BootLoader;
import ndfs.core.common.NettyClientBootstrap;
import ndfs.core.common.message.CreateBlockMasterResponseMessage;
import ndfs.core.common.message.CreateBlockMasterResponseMessage.SlaveServer;
import ndfs.core.common.message.CreateBlockMasterResultRequestMessage;
import ndfs.core.common.message.CreateBlockToSlaveRequest;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;
import ndfs.data.netty.server.NettyServerHandler;
import ndfs.dataserver.ClientSocketChannelCache;
import ndfs.dataserver.Global;
import ndfs.dataserver.SessionCache;
import ndfs.dataserver.TotalBlockInfoCache;
import ndfs.dataserver.UsedBlockInfoCache;
import ndfs.dataserver.WritableBlockInfoCache;
import ndfs.dataserver.model.BlockInfo;

/**
 * <p>
 * Description: 作为主data server，创建块的处理器
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月27日 下午4:00:01
 */
@Processor(msgType = MsgTypeEnum.CREATE_BLOCK_MASTER_RESPONSE)
public class CreateBlockMasterResponseProcessor
        extends ResponseMessageProcessor<CreateBlockMasterResponseMessage, CreateBlockMasterResultRequestMessage> {
    private static final Logger logger = LogUtils.getLogger(CreateBlockMasterResponseProcessor.class);

    private static final String keyForDataServerPort = "dataserver.port";

    @Override
    protected CreateBlockMasterResultRequestMessage process(ChannelHandlerContext channelHandlerContext,
            CreateBlockMasterResponseMessage responseMessage) {
        long blockCount = responseMessage.getBlockCount();
        long blockNumberMin = responseMessage.getBlockNumberMin();
        long sessionId = responseMessage.getSessionId();
        /** 向slave节点发送创建块的消息 **/
        List<SlaveServer> list = responseMessage.getSlaveServerList();
        int slaveNumber = list.size();

        for (SlaveServer slaveServer : list) {
            int id = slaveServer.getId();
            String ip = slaveServer.getIp();
            if (ClientSocketChannelCache.getSocketChannel(id) == null) {
                NettyClientBootstrap dataClient = new NettyClientBootstrap(ip,
                        BootLoader.getProperties(keyForDataServerPort), new NettyServerHandler(),
                        "data server连接到data server" + id + "成功");
                ClientSocketChannelCache.addSocketChannel(id, dataClient.getSocketChannel());
            }
            SocketChannel channel = ClientSocketChannelCache.getSocketChannel(id);
            channel.writeAndFlush(new CreateBlockToSlaveRequest(blockCount, blockNumberMin, sessionId));
        }
        /** 自己创建块 **/
        // TODO 选择块
        List<String> pathList = TotalBlockInfoCache.selectFreeBlockPathList((int) blockCount);
        long blockNumber = blockNumberMin;
        for (String path : pathList) {
            File oldFile = new File(path);
            File newFile = new File(oldFile.getParentFile().getAbsolutePath() + "block." + blockNumber);
            oldFile.renameTo(newFile);

            // 更新block 信息;
            BlockInfo blockInfo = new BlockInfo();
            blockInfo.init(blockNumber, newFile.getAbsolutePath());
            UsedBlockInfoCache.addBlockInfo(blockInfo);
            WritableBlockInfoCache.addBlockInfo(blockInfo);
            logger.info("master 创建块成功, id=%s", blockNumber);
            blockNumber++;
        }
        SessionCache.addSuccess(sessionId);

        /** 等待slave操作结果 **/
        boolean result = true;
        while (!SessionCache.isSuccess(sessionId, slaveNumber + 1)) {
            if (SessionCache.isFailure(sessionId)) {
                result = false;
                logger.error("slave 创建块失败");
                break;
            }
        }

        /** 返回给name server结果 **/
        CreateBlockMasterResultRequestMessage CreateBlockResultMasterRequest = null;
        List<Integer> dataserverList = new ArrayList<Integer>();
        dataserverList.add(Global.PORT);
        for (SlaveServer slaveServer : list) {
            dataserverList.add(slaveServer.getId());
        }
        if (result = true) {
            logger.info("所有data server创建块操作成功, 块id[%s-%s]", blockNumberMin, blockNumberMin + blockCount - 1);
            CreateBlockResultMasterRequest = new CreateBlockMasterResultRequestMessage(sessionId, true, blockNumberMin,
                    blockCount, dataserverList);
        } else {
            CreateBlockResultMasterRequest = new CreateBlockMasterResultRequestMessage(sessionId, false, 0, 0, dataserverList);
        }

        return CreateBlockResultMasterRequest;
    }

}
