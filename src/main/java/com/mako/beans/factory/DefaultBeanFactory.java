package com.mako.beans.factory;

import com.mako.beans.definition.BeanDefinition;
import com.mako.beans.definition.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) beanDefinitionNames.toArray();
    }
}
