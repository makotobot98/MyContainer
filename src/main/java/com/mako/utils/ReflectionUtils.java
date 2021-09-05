package com.mako.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setProperty(Object o, Class<?> objectClass, String fieldName, Object fieldValue) throws Exception {
        Field field = objectClass.getField(fieldName);
        field.set(o, fieldValue);
    }
}
