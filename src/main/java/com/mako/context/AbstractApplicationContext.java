package com.mako.context;

import com.mako.beans.factory.BeanFactory;
import com.mako.io.DefaultResourceLoader;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements BeanFactory,
        ApplicationContext {
    private String applicationName;


    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    /**
     * subclass returns their internal beanFactory here
     * @return beanFactory inside the application context
     */
    protected abstract BeanFactory getBeanFactory();

    public void refresh() {

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
     * TODO:
     * initialize beanFactory instance used by the IOC container
     */
    protected abstract void loadBeanFactory();

    /**
     * TODO: initialize the beanFactory, initialize non-lazy singletons from beanDefinitions
     */
    private void finishInitializeBeanFactory() {

    }

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
