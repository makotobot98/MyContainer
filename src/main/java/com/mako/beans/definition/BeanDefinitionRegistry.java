package com.mako.beans.definition;

import java.util.List;

/**
 * registry class for register bean definition into the IOC container, currently the only
 * implementing class is {@DefaultBeanFactory}
 */
public interface BeanDefinitionRegistry {
    /**
     * register a bean definition and store it into the IOC container
     * @param beanName bean name
     * @param beanDefinition bean definition instance to be registered
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * return a bean definition instance corresponding to the bean name
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * return a list of bean definition names stored in the IOC container
     * @return
     */
    List<String> getBeanDefinitionNames();
}
