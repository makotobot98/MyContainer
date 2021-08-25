package com.mako.context;

import com.mako.beans.definition.BeanDefinitionReader;
import com.mako.beans.definition.XmlBeanDefinitionReader;
import com.mako.beans.factory.BeanFactory;
import com.mako.beans.factory.DefaultBeanFactory;

public abstract class AbstractXmlApplicationContext extends AbstractApplicationContext {

    DefaultBeanFactory beanFactory;
    String configLocation;

    @Override
    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    protected void loadBeanFactory() {
        this.beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader();
        beanDefinitionReader.setBeanDefinitionRegistry(beanFactory);
        beanDefinitionReader.setResourceLoader(this);
        loadBeanDefinitions(beanDefinitionReader);
    }

    private void loadBeanDefinitions(XmlBeanDefinitionReader beanDefinitionReader) {
        String configLocation = getConfigLocation();
        if (configLocation != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocation);
        }
    }

    /**
     * returns the xml file path for the configuration file, this method shall be over-written by the subclass
     * @return string of path for the xml configuration location
     */
    public String getConfigLocation() {
        return null;
    }


}
