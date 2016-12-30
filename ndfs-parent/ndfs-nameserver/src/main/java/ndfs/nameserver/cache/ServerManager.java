
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ndfs.core.common.ToString;
import ndfs.core.common.message.LoginRequestMessage;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月27日 上午10:05:45
 */
public class ServerManager {
    private static Map<Integer, ServerInfo> serverInfoMap = new ConcurrentHashMap<Integer, ServerInfo>();

    public static void loadServerInfo(LoginRequestMessage loginRequestMessage, String ip) {
        int id = loginRequestMessage.getChannelId();
        long freeBlockCount = loginRequestMessage.getFreeBlockCount();
        serverInfoMap.put(id, new ServerInfo(id, ip, freeBlockCount));
    }
    
    public static int size() {
        return serverInfoMap.size();
    }

    /**
     * 
    * @Title: getMaxNFreeBlockCount 
    * @Description:  对server列表按照剩余空闲block数进行排序，找到n个block数最多的server,只保留大于1的server。
    * @param n
    * @return  
    * @return List<ServerInfo>
    * @throws
     */
    public static List<ServerInfo> getMaxNFreeBlockCount(int n) {
        List<ServerInfo> serverInfoList = new ArrayList<ServerInfo>();
        for (Map.Entry<Integer, ServerInfo> entry : serverInfoMap.entrySet()) {
            if (entry.getValue().getFreeBlockCount() > 0) {
                serverInfoList.add(entry.getValue());
            }
        }
        Collections.sort(serverInfoList, new Comparator<ServerInfo>() {
            @Override
            public int compare(ServerInfo o1, ServerInfo o2) {
                return o1.getFreeBlockCount() > o2.getFreeBlockCount() ? 1
                        : o1.getFreeBlockCount() == o2.getFreeBlockCount() ? 0 : -1;
            }
        });
        return serverInfoList;
    }

    public static void list() {

        System.out.println(serverInfoMap);
    }
    
    /**
     * 
    * @Title: getServerIp 
    * @Description:  根据server id获取ip地址
    * @param serverId
    * @return  
    * @return String
    * @throws
     */
    public static String getServerIp(int serverId) {
        return serverInfoMap.get(Integer.valueOf(serverId)).getIp();
    }

    public static class ServerInfo extends ToString {
        public ServerInfo(int id, String ip, long freeBlockCount) {
            super();
            this.id = id;
            this.ip = ip;
            this.freeBlockCount = freeBlockCount;
        }

        private int id;
        private String ip;
        private long freeBlockCount;

        public int getId() {

            return id;
        }

        public void setId(int id) {

            this.id = id;
        }

        public String getIp() {

            return ip;
        }

        public void setIp(String ip) {

            this.ip = ip;
        }

        public long getFreeBlockCount() {

            return freeBlockCount;
        }

        public void setFreeBlockCount(long freeBlockCount) {

            this.freeBlockCount = freeBlockCount;
        }
    }
}
