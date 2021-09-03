package com.mako.beans.definition;

public class AbstractBeanDefinition extends DefaultAttributeAccessor implements BeanDefinition {
    private Boolean isLazyInit = false;
    private Boolean isSingleton = false;
    private String className;


    @Override
    public void setBeanClassName(String beanClassName) {
    }

    @Override
    public String getBeanClassName() {
        return null;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {

    }

    @Override
    public boolean isLazyInit() {
        return false;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getBeanName() {
        return null;
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public Class<?> getBeanClass() {
        return null;
    }

    @Override
    public void setBeanClass(Class<?> clazz) {

    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        return null;
    }
}
