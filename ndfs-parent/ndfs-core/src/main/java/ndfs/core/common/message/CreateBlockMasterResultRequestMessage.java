
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: master创建块的结果，需要发送给name server
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月28日 上午11:13:57
 */
public class CreateBlockMasterResultRequestMessage extends RequestMessage {
    private long sessionId;
    private boolean success;
    private long blockNumberMin;
    private long blockCount;
    private List<Integer> dataserverList;

    public long getSessionId() {

        return sessionId;
    }

    public void setSessionId(long sessionId) {

        this.sessionId = sessionId;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {

        this.success = success;
    }

    public CreateBlockMasterResultRequestMessage(long sessionId, boolean success, long blockNumberMin, long blockCount,
            List<Integer> dataserverList) {
        super();
        this.msgType = MsgTypeEnum.CREATE_BLOCK_RESULT_MASTER_REQUEST.getCode();
        this.sessionId = sessionId;
        this.success = success;
        this.blockNumberMin = blockNumberMin;
        this.blockCount = blockCount;
        if (dataserverList == null) {
            this.dataserverList = new ArrayList<Integer>();
        } else {
            this.dataserverList = dataserverList;
        }
    }

    public CreateBlockMasterResultRequestMessage(long sessionId, boolean success, long blockNumberMin, long blockCount) {
        this(sessionId, success, blockNumberMin, blockCount, null);
    }

    public long getBlockNumberMin() {

        return blockNumberMin;

    }

    public void setBlockNumberMin(long blockNumberMin) {

        this.blockNumberMin = blockNumberMin;

    }

    public long getBlockCount() {

        return blockCount;

    }

    public void setBlockCount(long blockCount) {

        this.blockCount = blockCount;

    }

    public List<Integer> getDataserverList() {

        return dataserverList;

    }

    public void setDataserverList(List<Integer> dataserverList) {

        this.dataserverList = dataserverList;

    }
}
