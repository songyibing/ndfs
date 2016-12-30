
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.BootLoader;
import ndfs.core.common.StatusCodeEnum;

/**
 * <p>
 *     Description: 长连接Id
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 下午1:56:46
 */
public class ChannelId implements Serializable {
    
    
    public static final ChannelId DEFAULT = initializeChannel();
    private static final String keyForDataserverId = "dataserver.id";
    private static final String keyForDataserverPort = "dataserver.port";
    
    private Integer code;
     
    private String desc;
    
    private String ip;
    
    private int port;
    
    private static ChannelId initializeChannel() {
       // ChannelId channel = new ChannelId(code, desc, ip, port)
        int code = Integer.valueOf(BootLoader.getProperties(keyForDataserverId));
        String desc = "";
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
        }
        int port = Integer.valueOf(BootLoader.getProperties(keyForDataserverPort));
        return new ChannelId(code, desc, ip, port);
    }

    public Integer getCode() {
    
        return code;
    }

    public String getDesc() {
    
        return desc;
    }
    
    public String getIp() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
    
//    public static ChannelId getEnumByCode(Integer code) {
//        ChannelId[] values = ChannelId.values();
//        for(ChannelId channelId : values) {
//            if(channelId.getCode().equals(code)) {
//                return channelId;
//            }
//        }
//        throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
//    }

    private ChannelId(Integer code, String desc, String ip, int port) {
        this.code = code;
        this.desc = desc;
        this.ip = ip;
        this.port = port;
    }
    
    
}

    