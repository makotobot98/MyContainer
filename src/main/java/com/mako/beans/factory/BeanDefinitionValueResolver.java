package com.mako.beans.factory;

import com.mako.beans.definition.BeanDefinition;
import com.mako.beans.definition.RuntimeBeanReference;

/**
 * this class is delegated to resolve bean reference dependencies. It shall be used as a helper class
 * that assists bean factory to create beans from beanDefinitions
 */
public class BeanDefinitionValueResolver {
    private final AbstractBeanFactory beanFactory;
    private final String beanName;
    private final BeanDefinition beanDefinition;

    public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory, String beanName, BeanDefinition beanDefinition) {
        this.beanFactory = beanFactory;
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }

    public Object resolveRuntimeReferenceValue(RuntimeBeanReference reference) throws Exception {
        return beanFactory.getBean(reference.getBeanName());
    }
}
