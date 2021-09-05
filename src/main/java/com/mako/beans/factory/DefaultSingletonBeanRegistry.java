package com.mako.beans.factory;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //this is the cache that stores singleton beans that are finalized and initialized
    //any aop/proxy logic should be already applied to beans in this mapping
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**cache singleton beans for handling circular dependencie
     * beans here are usually:
     * 1. if any aop logic involved, proxy will already be applied
     * 2. properties of the beans are not yet populated (again, for handling circular dependency)
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * add the singleton bean into `singletonObjects` cache, the bean should be finalized and
     * if any aop logic is involved, the bean should already been proxied
     */
    public void addSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
        this.earlySingletonObjects.remove(beanName);
    }

    /**
     * add the singleton bean into the `earlySingletonObjects` cache, this is to resolve
     * circular dependency
     */
    public void addEarlySingleton(String beanName, Object singletonObject) {
        this.singletonObjects.remove(beanName);
        this.earlySingletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        Object bean = null;
        bean = singletonObjects.get(beanName);
        if (bean == null) {
            bean = earlySingletonObjects.get(beanName);
        }
        return bean;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public boolean containsSingleton(String beanName) {
        return getSingleton(beanName) != null;
    }

    @Override
    public String[] getSingletonNames() {
        return new String[0];
    }
}
