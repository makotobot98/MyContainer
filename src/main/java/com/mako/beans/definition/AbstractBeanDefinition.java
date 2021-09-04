package com.mako.beans.definition;

public class AbstractBeanDefinition extends DefaultAttributeAccessor implements BeanDefinition {
    private Boolean isLazyInit = false;
    private Boolean isSingleton = true;
    private String className;
    private PropertyValues propertyValues;
    private String beanId;
    private Class<?> clazz;

    @Override
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.className = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.className;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.isLazyInit = lazyInit;
    }

    @Override
    public boolean isLazyInit() {
        return this.isLazyInit;
    }

    @Override
    public boolean isSingleton() {
        return this.isSingleton;
    }

    @Override
    public String getBeanId() {
        return this.beanId;
    }

    @Override
    public void setBeanId(String name) {
        this.beanId = name;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.clazz;
    }

    @Override
    public void setBeanClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        return (MutablePropertyValues) this.propertyValues;
    }

    public void setIsSingleton(Boolean isSingleton) {
        this.isSingleton = isSingleton;
    }
}
