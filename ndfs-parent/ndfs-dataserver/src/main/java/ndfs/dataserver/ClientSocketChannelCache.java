
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import io.netty.channel.socket.SocketChannel;
import ndfs.core.cache.CacheBuilder;
import ndfs.core.cache.LocalCache;
import ndfs.dataserver.model.BlockInfo;

/**
 * <p>
 *     Description: 存放连接到其他data server的socket channel
 * </p>
 * @author yibingsong
 * @Date 2016年7月27日 下午4:42:06
 */
public class ClientSocketChannelCache {
public static final LocalCache<Integer, SocketChannel> INSTANCE = getInstance();
    
    private static LocalCache<Integer, SocketChannel> getInstance() {
        return CacheBuilder.newBuilder
                (CacheBuilder.DEFAULT_CACHE_BUILDER_TYPE, Integer.class, SocketChannel.class)
                .initialCapacity(800).build();
    }
    
    public static boolean isEmpty() {
        return INSTANCE.size() == 0;
    }
    
    public static SocketChannel getSocketChannel(int id) {
        return INSTANCE.get(id);
    }
    
    public static SocketChannel addSocketChannel(int id, SocketChannel sockChannel) {
        INSTANCE.put(id, sockChannel);
        return sockChannel;
    }
    
    
}

    