
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.core;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.eventbus.EventBus;

import ndfs.nameserver.event.EventListerner;

/**
 * <p>
 *     Description: 全局变量
 * </p>
 * @author yibingsong
 * @Date 2016年7月27日 下午1:53:02
 */
public class Global {
    // 事件总线，参考google guava eventBus
    public static final EventBus eventBus = initialEventBus();
    
    // session id，必须保证每次分配的值全局唯一。用于记录操作
    private static AtomicLong sessionId = new AtomicLong(1L);
    
    public static AtomicBoolean creatingBlockLock = new AtomicBoolean(false);
    
   
    
    private static EventBus initialEventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventListerner());
        return eventBus;
    }
    
    public static long createSessionId() {
        return sessionId.getAndIncrement();
    }
}

    