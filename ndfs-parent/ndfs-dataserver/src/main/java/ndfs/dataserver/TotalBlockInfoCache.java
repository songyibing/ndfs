
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.StatusCodeEnum;

/**
 * <p>
 * Description: 所有的块，包括未被初始化的块
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月26日 下午4:44:57
 */
public class TotalBlockInfoCache {

    private static Set<TotalBlockInfo> totalBlockInfoCache = new HashSet<TotalBlockInfo>();

    public static void addBlock(String path) {
        totalBlockInfoCache.add(new TotalBlockInfo(false, path));
    }

    public static class TotalBlockInfo {
        public TotalBlockInfo(boolean used, String path) {
            super();
            this.used = used;
            this.path = path;
        }

        private boolean used;
        private String path;

        public boolean isUsed() {

            return used;
        }

        public void setUsed(boolean used) {

            this.used = used;
        }

        public String getPath() {

            return path;
        }

        public void setPath(String path) {

            this.path = path;
        }

    }

    public static List<String> selectFreeBlockPathList(int count) {
        List<String> pathList = new ArrayList<String>(count);
        int _count = 0;
        for (TotalBlockInfo blockInfo : totalBlockInfoCache) {
            if (_count < count && !blockInfo.isUsed()) {
                pathList.add(blockInfo.getPath());
                _count++;
            }
        }
        // 理论上不会发生
        if (_count < count)
            throw new CommonException(StatusCodeEnum.UN_KNOWN_ERROR);
        return pathList;
    }
}
