package com.mako.beans.definition.namespaceHandler;

import com.mako.beans.definition.BeanDefinitionRegistry;
import com.mako.io.ResourceLoader;

/**
 * encapsulate configurations needed for xml element namespace handler
 */
public class ParserContext {
    private BeanDefinitionRegistry beanDefinitionRegistry;
    private ResourceLoader resourceLoader;

    public ParserContext(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader;
    }

    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
