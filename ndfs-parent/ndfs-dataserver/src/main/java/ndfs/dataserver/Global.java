
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import ndfs.core.common.BootLoader;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月28日 上午11:50:30
 */
public class Global {
    private static final String keyForDataserverId = "dataserver.id";
    
    public static volatile long freeBlockCount = 0;
    public static final int PORT = Integer.valueOf(BootLoader.getProperties(keyForDataserverId));
}

    