package com.mako.beans.factory;

import com.mako.beans.definition.*;
import com.mako.utils.ReflectionUtils;
import com.mako.utils.TypeConversionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DefaultBeanFactory is a class for managing bean definitions, and create bean instances based
 * on the bean definitions (it's probably better to split managing bean definition and create
 * bean to two classes to adhere single-class-responsibility principle)
 * - type conversion for bean properties will not be performed during bean definition loading,
 *   this means `beanDefinitionMap` will only store bean definitions that stores either
 *   string valued property or bean reference
 */
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
    public List<String> getBeanDefinitionNames() {
        return beanDefinitionNames;
    }

    @Override
    public Object createBean(String beanName) throws Exception {
        BeanDefinition bd = beanDefinitionMap.get(beanName);
        if (bd.isSingleton() && !bd.isLazyInit()) {
            Object bean = createSingletonBean(bd);
            return bean;
        }
        //TODO: implement create prototype beans
        return null;
    }

    private Object createSingletonBean(BeanDefinition bd) throws Exception {
        Class<?> beanClass = bd.getBeanClass();
        String beanName = bd.getBeanId();
        Object bean = beanClass.getDeclaredConstructor().newInstance();
        //handling circular dependency, expose early instance onto cache
        addEarlySingleton(beanName, bean);

        //populate properties
        populateBean(bean, bd);

        //apply post processors, any aop logic
        bean = applyPostProcessors(bean, bd);

        //add to the finalized bean cache
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * populate properties of a bean specified in its bean definition
     * - type conversions will be performed, and converted property will be set through {@ReflecitonUtils}
     * @param bean bean instance to be populated
     * @param bd corresponding bean definition
     */
    private void populateBean(Object bean, BeanDefinition bd) throws Exception {
        MutablePropertyValues propertyValues = bd.getPropertyValues();
        String beanName = bd.getBeanId();
        Class<?> beanClass = bd.getBeanClass();
        for (PropertyValue propertyValue : propertyValues) {
            String fieldName = propertyValue.getName();
            Object fieldValue = propertyValue.getValue();
            if (fieldValue instanceof RuntimeBeanReference) {
                BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(this, beanName, bd);
                Object resolvedValue = beanDefinitionValueResolver.resolveRuntimeReferenceValue((RuntimeBeanReference) fieldValue);
                ReflectionUtils.setProperty(bean, beanClass, fieldName, resolvedValue);
            } else if (fieldValue instanceof String) {
                Object convertedValue = convertIfNecessary(bd.getBeanClass(), fieldName, (String) fieldValue);
                ReflectionUtils.setProperty(bean, beanClass, fieldName, convertedValue);
            }
        }
    }

    private Object convertIfNecessary(Class<?> beanClass, String fieldName, String fieldValue) throws Exception {
        Field field = beanClass.getField(fieldName);
        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getTypeName();
        if (fieldTypeName.equals(String.class.getName())) {
            //if no conversion required
            return fieldValue;
        } else if (fieldTypeName.equals(Character.class.getName())) {
            return TypeConversionUtils.parseCharacter(fieldValue);
        } else if (fieldTypeName.equals(Boolean.class.getTypeName())) {
            return TypeConversionUtils.parseBoolean(fieldValue);
        } else {
            //parse number
            return TypeConversionUtils.parseNumber(fieldValue, fieldType);
        }
    }

    //TODO: implement AOP & proxying here
    private Object applyPostProcessors(Object bean, BeanDefinition bd) {
        return bean;
    }
}
