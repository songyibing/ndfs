
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.processor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ReadBlockRequestMessage;
import ndfs.core.common.message.ReadBlockResponseMessage;
import ndfs.dataserver.FileCache;
import ndfs.dataserver.UsedBlockInfoCache;
import ndfs.dataserver.model.BlockInfo;
import ndfs.dataserver.model.FileInfo;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年8月2日 上午10:24:03
 */
@Processor(msgType = MsgTypeEnum.READ_BLOCK_REQUEST)
public class ReadBlockRequestProcessor
        extends RequestMessageProcessor<ReadBlockRequestMessage, ReadBlockResponseMessage> {

    @Override
    protected ReadBlockResponseMessage process(ChannelHandlerContext channelHandlerContext,
            ReadBlockRequestMessage requestMessage) {
        long blockId = requestMessage.getBlockId();
        FileInfo fileInfo = FileCache.getFileInfo(requestMessage.getBlockId(), requestMessage.getFileId());
        long offset = fileInfo.getOffset();
        long length = fileInfo.getLength();

        BlockInfo blockInfo = UsedBlockInfoCache.getBlockInfo(blockId);
        byte[] content = new byte[(int) length];
        try {
            RandomAccessFile randomAccessFile = blockInfo.getReadFile();
            randomAccessFile.seek(offset);
            randomAccessFile.readFully(content);
        } catch (IOException e) {
            throw new CommonException(StatusCodeEnum.FILE_READ_ERROR, e.getMessage());
        }
        ReadBlockResponseMessage responseMessage = new ReadBlockResponseMessage();
        responseMessage.setContent(content);
        return responseMessage;

    }

}
