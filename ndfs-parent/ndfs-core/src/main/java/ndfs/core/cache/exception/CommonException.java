
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.cache.exception;

import org.apache.commons.lang3.StringUtils;

import ndfs.core.common.StatusCodeEnum;

/**
 * <p>
 * Description:缓存异常类
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月18日 下午3:30:47
 */
@SuppressWarnings("serial")
public class CommonException extends RuntimeException {
    // 结果码
    private StatusCodeEnum statusEnum;

    // 额外的异常信息
    private String errorMsg;

    public CommonException(StatusCodeEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public CommonException(StatusCodeEnum statusEnum, String msgSkeleton, String... params) {
        this.statusEnum = statusEnum;
        this.errorMsg = errorMsg;
        this.errorMsg = (msgSkeleton == null ? null : String.format(msgSkeleton, (Object[])params));
    }
    
    public CommonException(StatusCodeEnum statusEnum, String errorMsg) {
        this.statusEnum = statusEnum;
        this.errorMsg = errorMsg;
    }

    /**
     * 重写getMessage，附带结果枚举的信息
     */
    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(200);
        if (super.getMessage() != null) {
            sb.append(super.getMessage());
        }
        sb.append(" 异常原因：");
        sb.append(this.getStatusEnum().getStatusCode());
        sb.append("|").append(this.getStatusEnum().getMessage());
        if (StringUtils.isNotBlank(errorMsg)) {
            sb.append("|");
            sb.append(errorMsg);
        }
        return sb.toString();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public StatusCodeEnum getStatusEnum() {
        return statusEnum;
    }

}
