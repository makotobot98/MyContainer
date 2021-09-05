package com.mako.beans.factory;

public interface BeanFactory {
    /**
     * return a bean instance corresponding to the bean name
     * if the bean is not yet initialized, the bean will be first initialized and then
     * returned
     */
    Object getBean(String name) throws Exception;
}
