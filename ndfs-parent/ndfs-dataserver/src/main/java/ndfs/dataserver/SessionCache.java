
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.dataserver;

import java.io.ObjectOutputStream.PutField;
import java.util.concurrent.ConcurrentHashMap;

import ndfs.dataserver.SessionCache.SessionInfo;

/**
 * <p>
 * Description: 直接使用concurrentHashMap
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月27日 下午5:54:01
 */
public class SessionCache extends ConcurrentHashMap<Long, SessionInfo> {

    

    private static final long serialVersionUID = 1L;
    private static final SessionCache instance = new SessionCache();

    public static void addSuccess(long sessionId) {
        
        if (instance.get(sessionId) == null) {
            instance.putIfAbsent(sessionId, new SessionInfo(1, false));
        } else {
            boolean result = false;
            while (!result) {
                SessionInfo sessionInfoOld = instance.get(sessionId);
                SessionInfo sessionInfoNew = new SessionInfo(sessionInfoOld.getSucessNumber() + 1,
                        sessionInfoOld.isFailed());
                result = instance.replace(sessionId, sessionInfoOld, sessionInfoNew);
            }
        }
        return;
    }

    public static void addFailure(long sessionId) {
        if (instance.get(sessionId) == null) {
            instance.putIfAbsent(sessionId, new SessionInfo(1, false));
        } else {
            boolean result = false;
            while (!result) {
                SessionInfo sessionInfoOld = instance.get(sessionId);
                SessionInfo sessionInfoNew = new SessionInfo(sessionInfoOld.getSucessNumber(), true);
                result = instance.replace(sessionId, sessionInfoOld, sessionInfoNew);
            }
        }
    }

    // 尚未失败，返回false。否则，返回true。
    public static boolean isFailure(long sessionId) {
        SessionInfo sessionInfo = instance.get(sessionId);
        if (sessionInfo != null) {
            return sessionInfo.isFailed();
        }
        return false;
    }

    public static boolean isSuccess(long sessionId, int num) {
        SessionInfo sessionInfo = instance.get(sessionId);
        if (sessionInfo != null)
            if (sessionInfo.isFailed() == false)
                if (sessionInfo.getSucessNumber() == num)
                    return true;

        return false;
    }

    static class SessionInfo {
        public SessionInfo(int sucessNumber, boolean failed) {
            super();
            this.sucessNumber = sucessNumber;
            this.failed = failed;
        }

        // slave操作成功的个数
        private int sucessNumber;
        // 是否有slave操作失败
        private boolean failed;

        public int getSucessNumber() {

            return sucessNumber;
        }

        public void setSucessNumber(int sucessNumber) {

            this.sucessNumber = sucessNumber;
        }

        public boolean isFailed() {

            return failed;
        }

        public void setFailed(boolean failed) {

            this.failed = failed;
        }

        // concurrentHashMap的replace方法需要用到
        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            SessionInfo sessionInfo = (SessionInfo) o;
            if (sessionInfo.sucessNumber == this.sucessNumber && sessionInfo.failed == this.failed)
                return true;
            return false;

        }
    }

}
