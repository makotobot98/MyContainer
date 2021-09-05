package com.mako.context;

import com.mako.beans.factory.BeanFactory;

public interface ApplicationContext extends BeanFactory {
    String getApplicationName();
}
