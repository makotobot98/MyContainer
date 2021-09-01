package com.mako.beans.definition;

public interface BeanDefinition extends AttributeAccessor {
    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    boolean isSingleton();

    String getBeanName();

    void setBeanName(String name);

    public Class<?> getBeanClass();

    public void setBeanClass(Class<?> clazz);

    MutablePropertyValues getPropertyValues();
}
