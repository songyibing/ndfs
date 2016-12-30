
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.service;

import java.util.concurrent.atomic.AtomicBoolean;

import ndfs.nameserver.cache.BlockManager;
import ndfs.nameserver.core.Global;
import ndfs.nameserver.event.CreateBlockEvent;

/**
 * <p>
 * Description: 查看block信息的后台线程
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月26日 下午6:11:56
 */
public class BlockScanService extends Thread {
    // 是否在建立block块
    public static BlockScanService blockScanService;
    static {
        blockScanService = new BlockScanService();
    }
    public static void service() {
        blockScanService.run();
    }
    public void run() {
        while(true) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (BlockManager.needCreateBlock()) {
                tryCreateBlock();
            }
        }
    }

    private void tryCreateBlock() {
            if (Global.creatingBlockLock.get() == false) {
                if(Global.creatingBlockLock.compareAndSet(false, true)) {
                    Global.eventBus.post(new CreateBlockEvent());
                }
            }
    }
}
