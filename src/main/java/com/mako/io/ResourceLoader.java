package com.mako.io;

import java.io.InputStream;

public interface ResourceLoader {
    public InputStream getResourceAsStream(String path);

    public ClassLoader getClassLoader();
}
