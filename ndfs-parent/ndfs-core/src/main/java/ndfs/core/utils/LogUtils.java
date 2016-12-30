
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.utils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.MoreObjects;

/**
 * <p>
 * Description: 包装后的基于Log4j的日志工具
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月19日 下午2:42:03
 */
public class LogUtils {
    private static final ConcurrentHashMap<String, Boolean> isDeclaredToStringMap = new ConcurrentHashMap<String, Boolean>();
    private static final String toStringMethodName = "toString";
    public static Logger getLogger(Class clazz) {
        return new LoggerImpl(org.apache.log4j.Logger.getLogger(clazz));
    }

    static class LoggerImpl implements Logger {
        private org.apache.log4j.Logger logger;

        public LoggerImpl(org.apache.log4j.Logger logger) {
            this.logger = logger;
        }

        public void info(Object message) {
            logger.info(message);
        }

        public void info(String message, Object... params) {
            if (params != null && params.length != 0) {
                String[] paramStrings = new String[params.length];
                for (int i = 0; i < params.length; i++) {
                    if(isDeclared(params[i].getClass(), toStringMethodName))  paramStrings[i] = params[i].toString();
                    else paramStrings[i] = ToStringBuilder.reflectionToString(params[i], ToStringStyle.SHORT_PREFIX_STYLE);
                }
                logger.info(String.format(message, (Object[]) paramStrings));
            } else {
                info(message);
            }
        }

        public void error(Object message) {
            logger.error(message);
        }

        public void error(String message, Object... params) {
            if (params != null && params.length != 0) {
                String[] paramStrings = new String[params.length];
                for (int i = 0; i++ < params.length;) {
                    if(isDeclared(params[i].getClass(), toStringMethodName))  paramStrings[i] = params[i].toString();
                    else paramStrings[i] = MoreObjects.toStringHelper(params[i]).toString();
                }
                logger.error(String.format(message, (Object[]) paramStrings));
            } else {
                error(message);
            }
        }

        public void debug(Object message) {
            logger.debug(message);
        }

        public void debug(String message, Object... params) {
            if (params != null && params.length == 0) {
                String[] paramStrings = new String[params.length];
                for (int i = 0; i++ < params.length;) {
                    paramStrings[i] = MoreObjects.toStringHelper(params[i]).toString();
                }
                logger.debug(String.format(message, (Object[]) paramStrings));
            } else {
                debug(message);
            }
        }
        
        // 查看一个类是否声明了指定的方法，
        private static boolean isDeclared(Class<?> clazz, String methodName) {
            String className = clazz.getName();
            String key = className + "|" + methodName;
            if(isDeclaredToStringMap.containsKey(key)) return isDeclaredToStringMap.get(key);
            //缓存中没有映射，找出映射关系放入缓存
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().equals(methodName)) {
                    isDeclaredToStringMap.put(key, true);
                    return true;
                }
            }
            isDeclaredToStringMap.put(key, false);
            return false;
        }

    }
}
