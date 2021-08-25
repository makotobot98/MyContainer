package com.mako.context;

import com.mako.beans.factory.BeanFactory;

/**
 * subclass of {@AbstractXmlApplicationContext}
 * SimpleXmlApplicationContext simply sets the xml configuration locations through its constructor,
 * and the rest of the initializing beanFactory logic will leave to parent classes
 */
public class SimpleXmlApplicationContext extends AbstractXmlApplicationContext {
    private String configLocation;

    public SimpleXmlApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }
}