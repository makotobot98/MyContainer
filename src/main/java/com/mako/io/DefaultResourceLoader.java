package com.mako.io;

import com.mako.utils.ClassUtils;

import java.io.InputStream;

/**
 * Implementation of {@ResourceLoader}
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public InputStream getResourceAsStream(String path) {
        InputStream resourceAsStream = getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

    @Override
    public ClassLoader getClassLoader() {
        return ClassUtils.getClassLoader();
    }
}
