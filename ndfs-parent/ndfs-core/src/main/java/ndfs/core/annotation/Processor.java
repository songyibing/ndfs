
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ndfs.core.common.message.MsgTypeEnum;

/**
 * <p>
 * Description: 标记消息处理器，并指定处理的消息类型
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午2:50:31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface Processor {

    public MsgTypeEnum msgType() default MsgTypeEnum.NULL;

}
