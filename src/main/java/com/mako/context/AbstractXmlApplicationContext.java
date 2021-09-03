package com.mako.context;

import com.mako.beans.definition.BeanDefinitionReader;
import com.mako.beans.definition.XmlBeanDefinitionReader;
import com.mako.beans.factory.BeanFactory;
import com.mako.beans.factory.DefaultBeanFactory;
import org.dom4j.DocumentException;

public abstract class AbstractXmlApplicationContext extends AbstractApplicationContext {

    DefaultBeanFactory beanFactory;
    String configLocation;

    @Override
    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    protected void loadBeanFactory() throws DocumentException, ClassNotFoundException {
        this.beanFactory = createBeanFactory();
        //configure a xml bean definition reader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader();
        beanDefinitionReader.setBeanDefinitionRegistry(beanFactory);
        beanDefinitionReader.setResourceLoader(this);
        loadBeanDefinitions(beanDefinitionReader);
    }

    private DefaultBeanFactory createBeanFactory() {
        return new DefaultBeanFactory();
    }

    private void loadBeanDefinitions(XmlBeanDefinitionReader beanDefinitionReader) throws DocumentException, ClassNotFoundException {
        String configLocation = getConfigLocation();
        if (configLocation != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocation);
        }
    }

    /**
     * returns the xml file path for the xml configuration file storing the bean definition, this method shall be
     * over-written by the subclass
     * @return string of path for the xml configuration location
     */
    public String getConfigLocation() {
        return null;
    }


}
