package com.mako.beans.definition;

import java.util.Map;

public class SimpleBeanDefinition extends AbstractBeanDefinition {
    private static final String BEAN_LAZY_INIT_ATTR = "lazyInit";
    private static final String BEAN_SINGLETON_ATTR = "singleton";
    private SimpleBeanDefinition() {

    }

    public static class Builder {
        private SimpleBeanDefinition bd = new SimpleBeanDefinition();
        public Builder() {

        }
        public Builder attributes(Map<String, Object> attributes) {
            for (Map.Entry<String, Object> e : attributes.entrySet()) {
                String attributeName = e.getKey();
                Object attributeValue = e.getValue();
                switch (attributeName) {
                    case BEAN_LAZY_INIT_ATTR :
                        bd.setLazyInit((Boolean) attributeValue);
                        break;
                    case BEAN_SINGLETON_ATTR :
                        bd.setIsSingleton((Boolean) attributeValue);
                        break;
                    default:
                        break;
                }
            }
           return this;
        }
        public Builder className(String className) {
            bd.setBeanClassName(className);
            return this;
        }
        public Builder clazz(Class<?> clazz) {
            bd.setBeanClass(clazz);
            return this;
        }
        public Builder beanId(String name) {
            bd.setBeanId(name);
            return this;
        }
        public Builder propertyValues(PropertyValues pv) {
            bd.setPropertyValues(pv);
            return this;
        }
        public SimpleBeanDefinition build() {
            return this.bd;
        }
    }
}
