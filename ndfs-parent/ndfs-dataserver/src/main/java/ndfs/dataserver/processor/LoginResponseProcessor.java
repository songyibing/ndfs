
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver.processor;

import io.netty.channel.ChannelHandlerContext;
import ndfs.core.annotation.Processor;
import ndfs.core.common.message.LoginResponseMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.RequestMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description: 登录的回应消息
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午2:14:38
 */
@Processor(msgType = MsgTypeEnum.LOGIN_RESPONSE)
public class LoginResponseProcessor extends ResponseMessageProcessor<LoginResponseMessage, RequestMessage> {

    private static final Logger logger = LogUtils.getLogger(LoginResponseProcessor.class);

    @Override
    protected RequestMessage process(ChannelHandlerContext channelHandlerContext,
            LoginResponseMessage responseMessage) {
        logger.info("接收到 name server 的登录成功回应消息");
        return RequestMessage.nullObject();
    }
}
