
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import java.util.List;

import ndfs.core.common.model.BlockInfoInHeartBeat;

/**
 * <p>
 *     Description: 登录的请求消息
 * </p>
 * @author yibingsong
 * @Date 2016年7月20日 上午10:21:26
 */
public class LoginRequestMessage extends RequestMessage{
    
    private String userName;
    
    private String password;
    
    @Override
    protected void initialize() {
        super.initialize();
        userName = "root";
        password = "root";
        this.msgType = MsgTypeEnum.LOGIN_REQUEST.getCode();
    }
    
    public List<BlockInfoInHeartBeat> getBlockInfoList() {
        
        return blockInfoList;
            
    }

    public void setBlockInfoList(List<BlockInfoInHeartBeat> blockInfoList) {
        
        this.blockInfoList = blockInfoList;
            
    }

    public long getFreeBlockCount() {
        
        return freeBlockCount;
            
    }

    public void setFreeBlockCount(long freeBlockCount) {
        
        this.freeBlockCount = freeBlockCount;
            
    }

    private List<BlockInfoInHeartBeat> blockInfoList;
    
    private long freeBlockCount;

  
    
    
}

    