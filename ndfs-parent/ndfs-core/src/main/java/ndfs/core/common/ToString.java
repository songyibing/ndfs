
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <p>
 *     Description: 重写toString()
 * </p>
 * @author yibingsong
 * @Date 2016年7月27日 上午11:29:58
 */
public class ToString implements Serializable{
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


    