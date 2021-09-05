package com.mako.beans.factory;

import com.mako.io.DefaultResourceLoader;

/**
 * AbstractBeanFactory will handle logic on getting initialized beans from the cache,
 * if a bean is not in the cache, a new bean will be instantiated, cached, and returned.
 * create bean logic is delegated to subclass that's responsible for managing bean definitions
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws Exception {
        return doGetBean(name);
    }

    public Object doGetBean(String name) throws Exception {
        Object bean = getSingleton(name);
        if (bean == null) {
            bean = createBean(name);
        }
        return bean;
    }

    public abstract Object createBean(String beanName) throws NoSuchMethodException, Exception;
}
