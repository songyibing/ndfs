
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.processor;

import java.io.RandomAccessFile;

import org.omg.CORBA.Current;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.ResponseMessage;
import ndfs.core.common.message.WriteBlockRequestMessage;
import ndfs.core.common.message.WriteBlockResponseMessage;
import ndfs.dataserver.FileCache;
import ndfs.dataserver.UsedBlockInfoCache;
import ndfs.dataserver.WritableBlockInfoCache;
import ndfs.dataserver.model.BlockInfo;

/**
 * <p>
 *     Description: master处理写入文件的请求，暂时只向master写入
 * </p>
 * @author yibingsong
 * @Date 2016年8月1日 下午1:31:21
 */
@Processor(msgType = MsgTypeEnum.WRITE_BLOCK_REQUEST)
public class WriteBlockRequestProcessor extends RequestMessageProcessor<WriteBlockRequestMessage, ResponseMessage>{

    @Override
    protected ResponseMessage process(ChannelHandlerContext channelHandlerContext,
            WriteBlockRequestMessage requestMessage) {
        long blockId = requestMessage.getBlockId();
        byte[] fileContent = requestMessage.getFileContent();
        BlockInfo blockInfo = WritableBlockInfoCache.INSTANCE.get(blockId);
        RandomAccessFile randomAccessFile  = blockInfo.getWriteFile();
        try {
            randomAccessFile.seek(blockInfo.getActualSize());
            randomAccessFile.write(fileContent);
        } catch (Exception e) {
            throw new CommonException(StatusCodeEnum.FILE_WRITE_ERROR);
        }
        
        FileCache.addFileInfo(blockId, blockInfo.getCurrentFileId() + 1, blockInfo.getActualSize(), fileContent.length);
        blockInfo.setActualSize( blockInfo.getActualSize() + fileContent.length);
        blockInfo.setCurrentFileId(blockInfo.getCurrentFileId() + 1);
        WritableBlockInfoCache.addBlockInfo(blockInfo);
        UsedBlockInfoCache.addBlockInfo(blockInfo);
        
        String path = blockId + "|" + blockInfo.getCurrentFileId();
        WriteBlockResponseMessage responseMessage = new WriteBlockResponseMessage();
        responseMessage.setFilePath(path);
        responseMessage.setUuid(requestMessage.getUuid());
        return responseMessage;
            
    }

}

    