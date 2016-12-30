
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package org.ndfs.core;
/**
 * <p>
 *     Description: 测试declaredMethod方法
 * </p>
 * @author yibingsong
 * @Date 2016年7月21日 下午12:42:28
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class ReflectTest extends TestCase{
    public static final String TOSTRING = "overide toString method";
    
    public void testDeclaredMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ToString toString = new ToString();
        Method method = toString.getClass().getDeclaredMethod("toString", null);
        assertEquals(method.invoke(toString, new Object[0]), TOSTRING);
        
     
        NotToString notToString = new NotToString();
        try {
            method = notToString.getClass().getDeclaredMethod("toString", null);
        } catch (NoSuchMethodException e) {
            assertEquals(true, true);
            return;
        }
        assertEquals(true, false);
    }
}

class ToString {
    public String toString() {
        return ReflectTest.TOSTRING;
    }
}

class NotToString {}

    