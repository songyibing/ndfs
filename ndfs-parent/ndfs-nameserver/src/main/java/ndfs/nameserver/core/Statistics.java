
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.nameserver.core;

/**
 * <p>
 *     Description: 
 * </p>
 * @author yibingsong
 * @Date 2016年7月20日 下午6:13:28
 */
@SuppressWarnings("restriction")
public class Statistics {
    
    public static final Statistics INSTANCE = new Statistics();
    
    public volatile long currentMaxBlockNumber;

    public static long getCurrentMaxBlockNumber() {
        return INSTANCE.currentMaxBlockNumber;
            
    }

    //对currentMaxBlockNumber 做CAS操作
    public static boolean casCurrentMaxBlockNumber(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(INSTANCE, currentMaxBlockNumberOffset, cmp, val);
    }
    
    private static final sun.misc.Unsafe UNSAFE;
    private static final long currentMaxBlockNumberOffset;
    static {
        try {
            UNSAFE = getUnsafe();
            Class<?> sk = Statistics.class;
            currentMaxBlockNumberOffset = UNSAFE.objectFieldOffset
                (sk.getDeclaredField("currentMaxBlockNumber"));
         
            
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * Returns a sun.misc.Unsafe.  Suitable for use in a 3rd party package.
     * Replace with a simple call to Unsafe.getUnsafe when integrating
     * into a jdk.
     *
     * @return a sun.misc.Unsafe
     */
    @SuppressWarnings("restriction")
    private static sun.misc.Unsafe getUnsafe() {
        try {
            return sun.misc.Unsafe.getUnsafe();
        } catch (SecurityException tryReflectionInstead) {}
        try {
            return java.security.AccessController.doPrivileged
            (new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() {
                public sun.misc.Unsafe run() throws Exception {
                    Class<sun.misc.Unsafe> k = sun.misc.Unsafe.class;
                    for (java.lang.reflect.Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (k.isInstance(x))
                            return k.cast(x);
                    }
                    throw new NoSuchFieldError("the Unsafe");
                }});
        } catch (java.security.PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics",
                                       e.getCause());
        }
    }
}

    