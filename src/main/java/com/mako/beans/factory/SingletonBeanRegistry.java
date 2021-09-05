package com.mako.beans.factory;

import com.mako.utils.ClassUtils;

public interface SingletonBeanRegistry {
    /**
     * returns the singleton bean matching the beanName
     */
    Object getSingleton(String beanName);

    /**
     * register a singleton bean instance and stored it in an internal representation
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * returns if the container has an corresponding instantiated singleton bean
     */
    boolean containsSingleton(String beanName);

    /**
     * return a list of instantiated singleton bean names
     */
    String[] getSingletonNames();


}
