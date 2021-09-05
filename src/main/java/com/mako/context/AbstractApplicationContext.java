package com.mako.context;

import com.mako.beans.factory.BeanFactory;
import com.mako.io.DefaultResourceLoader;
import org.dom4j.DocumentException;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements BeanFactory,
        ApplicationContext {
    private String applicationName;


    @Override
    public Object getBean(String name) throws Exception {
        return getBeanFactory().getBean(name);
    }

    /**
     * subclass returns their internal beanFactory here
     * @return beanFactory inside the application context
     */
    protected abstract BeanFactory getBeanFactory();

    public void refresh() throws Exception {

        //pre process bean factory
        prepareRefresh();

        //initialize the bean factory
        loadBeanFactory();

        //post initialize non lazy beans
        finishInitializeBeanFactory();

        //post process bean factory
        postRefresh();
    }

    /**
     *
     * initialize beanFactory instance used by the IOC container, load the bean definitions
     * in the user specified configuration files. The process of bean definition loading will be
     * delegated to the beanFactory instance and a BeanDefinitionReader
     */
    protected abstract void loadBeanFactory() throws DocumentException, ClassNotFoundException, Exception;

    /**
     * initialize the beanFactory, initialize non-lazy singletons from beanDefinitions,
     * this will be delegated to the class that's responsible for managing bean definitions
     */
    private void finishInitializeBeanFactory() throws Exception {
        preInstantiateSingletons();
    }

    /**
     * this is the method that subclass that manages the bean definitions should implement,
     * the container will check all non-lazy initialized singleton bean definitions and initialize them
     */
    protected abstract void preInstantiateSingletons() throws Exception;

    /**
     * subclass add their custom logic for preprocessing before refresh()
     */
    protected void prepareRefresh() {
        return;
    }

    /**
     * subclass add their custom logic for post-processing after refresh()
     */
    public void postRefresh() {
        return;
    }

    @Override
    public String getApplicationName() {
        return this.applicationName == null ? "" : this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

}
