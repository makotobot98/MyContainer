package com.mako.context;

import com.mako.beans.definition.BeanDefinition;
import com.mako.beans.definition.BeanDefinitionReader;
import com.mako.beans.definition.XmlBeanDefinitionReader;
import com.mako.beans.factory.BeanFactory;
import com.mako.beans.factory.DefaultBeanFactory;
import org.dom4j.DocumentException;

import java.util.List;

public abstract class AbstractXmlApplicationContext extends AbstractApplicationContext {

    DefaultBeanFactory beanFactory;
    String configLocation;

    @Override
    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    protected void loadBeanFactory() throws Exception {
        this.beanFactory = createBeanFactory();
        //configure a xml bean definition reader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader();
        beanDefinitionReader.setBeanDefinitionRegistry(beanFactory);
        beanDefinitionReader.setResourceLoader(this);
        loadBeanDefinitions(beanDefinitionReader);
    }

    @Override
    protected void preInstantiateSingletons() throws Exception {
        List<String> beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition bd = beanFactory.getBeanDefinition(beanDefinitionName);
            //if the bean is singleton and need to be initialized non-lazily
            if (bd.isSingleton() && !bd.isLazyInit()) {
                //check if already created
                String beanName = bd.getBeanId();
                getBean(beanName);
            }
        }
    }

    private DefaultBeanFactory createBeanFactory() {
        return new DefaultBeanFactory();
    }

    private void loadBeanDefinitions(XmlBeanDefinitionReader beanDefinitionReader) throws Exception {
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
