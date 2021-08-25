package com.mako.beans.definition;

import com.mako.io.ResourceLoader;

public interface BeanDefinitionReader {
    /**
     * given a location path to the bean definition files, load the bean definitions using those files into beanFactory
     * @param location string path location to the configuration file
     */
    void loadBeanDefinitions(String location);

    /**
     *
     * @return resource loader to load bean definition files
     */
    ResourceLoader getResourceLoader();

    /**
     *
     * @return registry that can register bean definition
     */
    BeanDefinitionRegistry getBeanDefinitionRegistry();

}
