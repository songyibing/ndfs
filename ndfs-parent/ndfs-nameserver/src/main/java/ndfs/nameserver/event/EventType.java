
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.event;
/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月27日 下午1:50:46
 */
public enum EventType {
    CREATE_BLOCK(1, "创建块");
    
    private int code;
    private String desc;
    private EventType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}

    