package com.mako.utils;

public abstract class ClassUtils {
    public static ClassLoader getClassLoader() {
        return ClassUtils.class.getClassLoader();
    }
}
