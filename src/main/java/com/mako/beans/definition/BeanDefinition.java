package com.mako.beans.definition;

public interface BeanDefinition extends AttributeAccessor {
    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    boolean isSingleton();

    String getBeanId();

    void setBeanId(String name);

    public Class<?> getBeanClass();

    public void setBeanClass(Class<?> clazz);

    public void setPropertyValues(PropertyValues propertyValues);

    MutablePropertyValues getPropertyValues();
}
