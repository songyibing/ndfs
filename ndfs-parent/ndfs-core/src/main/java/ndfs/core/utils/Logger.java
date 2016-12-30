
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.utils;

import com.google.common.base.MoreObjects;

/**
 * <p>
 *     Description: 日志接口
 * </p>
 * @author yibingsong
 * @Date 2016年7月19日 下午3:33:02
 */
public interface Logger {
    
    public void info(Object message);

    public void info(String message, Object... params);
      

    public void error(Object message);

    public void error(String message, Object... params);
}

    