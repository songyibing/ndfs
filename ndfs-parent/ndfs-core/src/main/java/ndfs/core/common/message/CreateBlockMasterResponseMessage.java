
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import java.util.List;

/**
 * <p>
 *     Description: name server 向data master server 发送创建block的消息
 * </p>
 * @author yibingsong
 * @Date 2016年7月26日 上午11:45:38
 */
public class CreateBlockMasterResponseMessage extends ResponseMessage{
    
    private long blockCount;
    
    private long blockNumberMin;
    
    private List<SlaveServer> slaveServerList;
    
    private long sessionId;
    
    public CreateBlockMasterResponseMessage(long blockCount, long blockNumberMin, List<SlaveServer> slaveServerList, long sessionId) {
        super();
        this.msgType = MsgTypeEnum.CREATE_BLOCK_MASTER_RESPONSE.getCode();
        this.blockCount = blockCount;
        this.blockNumberMin = blockNumberMin;
        this.slaveServerList = slaveServerList;
        this.sessionId = sessionId;
    }

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.CREATE_BLOCK_MASTER_RESPONSE.getCode();
    }
    
    public static class SlaveServer {
        private String ip;
        private int id;
        public SlaveServer(String ip, int id) {
            super();
            this.ip = ip;
            this.id = id;
        }
        public String getIp() {
        
            return ip;
        }
        public void setIp(String ip) {
        
            this.ip = ip;
        }
        public int getId() {
        
            return id;
        }
        public void setId(int id) {
        
            this.id = id;
        }
    }

    public long getBlockCount() {
    
        return blockCount;
    }

    public void setBlockCount(long blockCount) {
    
        this.blockCount = blockCount;
    }

    public long getBlockNumberMin() {
    
        return blockNumberMin;
    }

    public void setBlockNumberMin(long blockNumberMin) {
    
        this.blockNumberMin = blockNumberMin;
    }

    public List<SlaveServer> getSlaveServerList() {
    
        return slaveServerList;
    }

    public void setSlaveServerList(List<SlaveServer> slaveServerList) {
    
        this.slaveServerList = slaveServerList;
    }

    public long getSessionId() {
        
        return sessionId;
            
    }

    public void setSessionId(long sessionId) {
        
        this.sessionId = sessionId;
            
    }
}

    