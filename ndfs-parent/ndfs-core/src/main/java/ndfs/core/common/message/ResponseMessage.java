
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common.message;
/**
 * <p>
 *     Description: 把name server发给 block server的消息称为response message
 * </p>
 * @author yibingsong
 * @Date 2016年7月20日 上午10:08:05
 */
public abstract class ResponseMessage extends CommonMessage{

    
    private static final ResponseMessage NULL_RESPONSE_MESSAGE = new NullResponseMessage();
    
    public static ResponseMessage nullMessage() {return NULL_RESPONSE_MESSAGE;}
    
    static class NullResponseMessage extends ResponseMessage{
        @Override
        protected void initialize() {}
    }

}

    