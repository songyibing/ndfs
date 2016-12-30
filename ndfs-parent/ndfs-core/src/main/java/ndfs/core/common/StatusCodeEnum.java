
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common;

/**
 * <p>
 * Description: 状态码枚举，包括状态码和对应的描述
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月18日 下午3:42:11
 */
public enum StatusCodeEnum {
    
    
    UN_KNOWN_ERROR(0000, "未知错误"),
    
    
    
    LOAD_PROCESSOR_ERROR(1001, "载入processor失败"),
    
    LOAD_PROPERTIES_ERROR(1002, "载入processor失败"),
    
    CACHE_GET_ERROR(1003, "获取缓存值失败"),
    
    THREAD_INTERRUPT_ERROR(1004, "线程中断"),
    
    FILE_CREATE_ERROR(2001, "文件创建失败"),
    
    FILE_OPEN_ERROR(2002, "文件打开失败"),
    
    
    
    FILE_WRITE_ERROR(2003, "文件写入失败"),
    
    FILE_READ_ERROR(2004, "文件读取失败");
    
    
    
    

    private int statusCode;

    private String message;

    public int getStatusCode() {

        return statusCode;
    }

    public void setStatusCode(int statusCode) {

        this.statusCode = statusCode;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    private StatusCodeEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
