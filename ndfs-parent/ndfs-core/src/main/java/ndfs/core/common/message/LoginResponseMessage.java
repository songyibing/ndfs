
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;
/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月20日 下午1:31:50
 */
public class LoginResponseMessage extends ResponseMessage{

    @Override
    protected void initialize() {
        this.msgType = MsgTypeEnum.LOGIN_RESPONSE.getCode();
    }
    
}

    