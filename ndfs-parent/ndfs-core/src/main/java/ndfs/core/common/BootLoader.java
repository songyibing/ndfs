
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.common;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;

import ndfs.core.annotation.Processor;
import ndfs.core.cache.exception.CommonException;
import ndfs.core.common.message.CommonMessage;
import ndfs.core.utils.LogUtils;
import ndfs.core.utils.Logger;

/**
 * <p>
 * Description: 启动时初始化的数据
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月20日 下午3:41:05
 */
public class BootLoader {
    

    private static final Logger logger = LogUtils.getLogger(BootLoader.class);

    // 属性值
    private static final Properties properties = loadProperties();

    // 处理器的缓存
    @SuppressWarnings("unchecked")
    private static final HashMap<Integer, MessageProcessor<? extends CommonMessage>> processorMap = loadProcessor();

    // 处理器包名
    private static final String keyForProcessorPackage = "processor.package";

    //

    

    private static Properties loadProperties() {
        String path = Thread.currentThread().getContextClassLoader().getResource("conf.properties").getPath();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (Exception e) {
            throw new CommonException(StatusCodeEnum.LOAD_PROPERTIES_ERROR);
        }
        properties.list(System.out);
        return properties;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static HashMap loadProcessor() {
        HashMap<Integer, MessageProcessor<?>> processorMap = new HashMap<Integer, MessageProcessor<?>>();
        try {
            Set<Class<?>> processorSet = new Reflections(properties.getProperty(keyForProcessorPackage))
                    .getTypesAnnotatedWith(Processor.class);
            for (Class clazz : processorSet) {
                Processor processorAnnotation = (Processor) clazz.getAnnotation(Processor.class);
                Integer code = processorAnnotation.msgType().getCode();
                processorMap.put(code, (MessageProcessor<?>) clazz.newInstance());
                logger.info("载入Processor[%s]", clazz.getSimpleName());
            }
        } catch (Exception e) {
            throw new CommonException(StatusCodeEnum.LOAD_PROCESSOR_ERROR);
        }
        return processorMap;
    }

    public static MessageProcessor getProcessor(Integer channelId) {
        return processorMap.get(channelId);
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }

}
