package com.mako.context;

import com.mako.beans.factory.BeanFactory;
import org.dom4j.DocumentException;

/**
 * subclass of {@AbstractXmlApplicationContext}
 * SimpleXmlApplicationContext simply sets the xml configuration locations through its constructor,
 * and the rest of the initializing beanFactory logic will leave to parent classes
 */
public class SimpleXmlApplicationContext extends AbstractXmlApplicationContext {
    private String configLocation;

    public SimpleXmlApplicationContext(String configLocation) throws DocumentException, ClassNotFoundException {
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }
}
