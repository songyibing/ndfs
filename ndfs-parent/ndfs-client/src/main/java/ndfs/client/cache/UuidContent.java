
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.client.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2017年1月7日 下午4:18:10
 */
public class UuidContent extends ConcurrentHashMap<String, String>{
    private static final UuidContent INSTANCE = new UuidContent();
    public static UuidContent getInstance() {return INSTANCE;}
}

    