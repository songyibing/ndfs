
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;

import java.util.HashMap;

import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 下午7:39:46
 */
public enum MsgTypeEnum {
    
    
    NULL(-1, "避免空指针"),
    
    /*********************data server发送的消息******************/
    HEARTBEAT_REQUST(1001, "data server发送的心跳消息"),
    
    LOGIN_REQUEST(1002, "data server发送的登录请求"),
    
    CREATE_BLOCK_RESULT_MASTER_REQUEST(1003, "data server发送的创建块结果消息"),
    
    WRITE_BLOCK_RESPONSE(1004, "data server发送的文件写入结果的消息"),
    
    READ_BLOCK_RESPONSE(1005, "data server返回的文件数据"),
    
    
    /*********************name server发送的消息******************/
    HEARTBEAT_RESPONSE(2001, "name server回应的心跳消息"),
    
    LOGIN_RESPONSE(2002, "name server回应的登录请求"),
    
    CREATE_BLOCK_MASTER_RESPONSE(2003, "name server发送的创建块消息"),
    
    OBTAIN_WRITABLE_BLOCK_RESPONSE(2004, "client上传文件时，name server返回的block id消息"),
    
    OBTAIN_READABLE_SERVER_RESPONSE(2005, "读取文件时，返回给client的server信息"),
    
    
    
    
    /*********************client发送的消息******************/
    OBTAIN_WRITABLE_BLOCK_REQUEST(3001, "client上传文件时，向name server获取block id的消息"),
    
    WRITE_BLOCK_REQUEST(3002, "写入block的请求"),
    
    OBTAIN_READABLE_SERVER_REQUEST(3003, "读取文件时，根据block id获取server ip"),
    
    READ_BLOCK_REQUEST(3004, "从block读文件请求");

    private int code;

    private String desc;

    public int getCode() {

        return code;

    }

    public void setCode(int code) {

        this.code = code;

    }

    public String getDesc() {

        return desc;

    }

    public void setDesc(String desc) {

        this.desc = desc;

    }

    private MsgTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    
    private static final Logger logger = LogUtils.getLogger(MsgTypeEnum.class);
    
    private static final HashMap<Integer, MsgTypeEnum> msgTypeEnumMap = getMap();
    
    private static HashMap<Integer, MsgTypeEnum> getMap() {
        HashMap<Integer, MsgTypeEnum> map = new HashMap<Integer, MsgTypeEnum>();
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for(MsgTypeEnum msgTypeEnum : values) {
            map.put(msgTypeEnum.getCode(), msgTypeEnum);
            logger.info("载入消息类型[%s:%s]", msgTypeEnum.getCode(), msgTypeEnum.getDesc());
        }
        return map;
    }
    
    public static MsgTypeEnum getMsgTypeEnumByCode(int code) {
        if(!msgTypeEnumMap.containsKey(code)) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
        }
        return msgTypeEnumMap.get(code);
        
    }
    
    //用于日志打印
    public String toString() {
        return getCode() + " : " + getDesc();
    }

}
