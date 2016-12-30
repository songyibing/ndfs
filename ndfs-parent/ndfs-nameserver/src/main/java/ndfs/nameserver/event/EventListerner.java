
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.event;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import io.netty.channel.socket.SocketChannel;
import ndfs.core.cache.ChannelCache;
import ndfs.core.common.BootLoader;
import ndfs.core.common.message.CreateBlockMasterResponseMessage;
import ndfs.core.common.message.MsgTypeEnum;
import ndfs.core.common.message.CreateBlockMasterResponseMessage.SlaveServer;
import ndfs.nameserver.cache.ServerManager;
import ndfs.nameserver.cache.ServerManager.ServerInfo;
import ndfs.nameserver.core.Global;
import ndfs.nameserver.core.Statistics;

/**
 * <p>
 * Description: 时间监听器
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月27日 下午1:02:29
 */
public class EventListerner {
    // 每次创建的block数目
    private static final String keyForCreateBlockCount = "createBlock.count";

    // 备份的数目
    private static final String keyForCopiesNumber = "copies.number";

    @Subscribe
    public void createBlock(CreateBlockEvent event) {
        // 创建block前已经获取锁，无需考虑线程安全问题
        int copiesNumber = Integer.valueOf(BootLoader.getProperties(keyForCopiesNumber));
        int createBlockCount = Integer.valueOf(BootLoader.getProperties(keyForCreateBlockCount));
        List<ServerInfo> maxFreeBlockServers = ServerManager.getMaxNFreeBlockCount(copiesNumber);
        if(maxFreeBlockServers.size() < copiesNumber) {
            copiesNumber = maxFreeBlockServers.size();
        }
        if(maxFreeBlockServers.get(copiesNumber - 1).getFreeBlockCount() < createBlockCount) {
            createBlockCount = (int) maxFreeBlockServers.get(copiesNumber - 1).getFreeBlockCount();
        }
        //TODO 选取master 这里暂时以第一个作为master
        SocketChannel socketChannel = ChannelCache.getChannel(maxFreeBlockServers.get(0).getId());
        List<SlaveServer> slaveServerList = new ArrayList<SlaveServer>();
        for(int i=1; i< copiesNumber; i++) {
            ServerInfo serverInfo = maxFreeBlockServers.get(i);
            slaveServerList.add(new SlaveServer(serverInfo.getIp(), serverInfo.getId()));
        }
        long currentMaxBlockNumber = Statistics.INSTANCE.getCurrentMaxBlockNumber();
        CreateBlockMasterResponseMessage message = new CreateBlockMasterResponseMessage(createBlockCount, currentMaxBlockNumber + 1, slaveServerList, Global.createSessionId());
        socketChannel.writeAndFlush(message);
    }

 
}
