package com.mako.beans.definition;

import java.util.Objects;

public class RuntimeBeanReference implements BeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuntimeBeanReference that = (RuntimeBeanReference) o;
        return beanName.equals(that.beanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName);
    }
}
