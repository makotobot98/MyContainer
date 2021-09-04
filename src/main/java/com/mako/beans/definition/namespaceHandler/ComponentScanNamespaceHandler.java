package com.mako.beans.definition.namespaceHandler;


import com.mako.annotations.Autowired;
import com.mako.annotations.Service;
import com.mako.annotations.Value;
import com.mako.beans.definition.*;
import com.mako.io.Resource;
import com.mako.utils.ClassUtils;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentScanNamespaceHandler implements NamespaceHandler {
    private final String BASE_PACKAGE = "base-package";
    public final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private String RESOURCE_PATTERN = "**/*.class";

    @Override
    public void init() {}

    /**
     * parse a xml tag of <component-scan base-package=".."></component-scan>
     * 1. load all annotated resources under the base-package
     * 2. convert all annotated beans into bean definition
     *  - convert into a class<?>
     *  - convert a class definition into bean definition
     * 3. register all parsed bean definitions through BeanDefinitionRegistry
     * @param e xml dom4j's element <component-scan></component-scan>
     * @param parserContext parser context that encapsulates the configuration needed to parse
     */
    @Override
    public void parse(Element e, ParserContext parserContext) throws Exception {
        String packageName = e.attributeValue(BASE_PACKAGE);
        List<Class> candidateClasses = getClasses(packageName);
        for (Class c : candidateClasses) {
            //load the bean definition of annotated class
            if (isAnnotatedBeanClass(c)) {
                loadBeanDefinition(c, parserContext.getBeanDefinitionRegistry());
            }
        }
    }

    private void loadBeanDefinition(Class clazz, BeanDefinitionRegistry beanDefinitionRegistry) throws Exception {
        String className = clazz.getName();
        String beanId = parseBeanId(clazz);
        PropertyValues propertyValues = parsePropertyValues(clazz);
        SimpleBeanDefinition bd = new SimpleBeanDefinition.Builder()
                .clazz(clazz)
                .className(className)
                .beanId(beanId)
                .propertyValues(propertyValues)
                .build();
        registerAnnotatedBeanDefinition(beanDefinitionRegistry, bd);
    }

    private void registerAnnotatedBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry, BeanDefinition bd) {
        beanDefinitionRegistry.registerBeanDefinition(bd.getBeanId(), bd);
    }

    private PropertyValues parsePropertyValues(Class clazz) throws Exception {
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            if (field.isAnnotationPresent(Value.class)) {
                //if user specified field value to be initialized using {@Value}
                Value fieldValueAnnotation = field.getAnnotation(Value.class);
                String value = fieldValueAnnotation.value();
                if (requireTypeConversion(fieldType, value)) {
                    Method valueOfMethod = fieldType.getMethod("valueOf", String.class);
                    Object convertedFieldValue = valueOfMethod.invoke(null, value);
                    propertyValues.add(fieldName, convertedFieldValue);
                } else {
                    propertyValues.add(fieldName, value);
                }
            } else if (field.isAnnotationPresent(Autowired.class)){
                //if autowired type
                Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                //if user specified a autowired bean ref id, use it
                //if not specified, use class name with first character lower cased
                String beanId = autowiredAnnotation.beanId();
                if (beanId == null || beanId.isEmpty()) {
                    String fieldFullTypeName = fieldType.getTypeName();
                    String[] splits = fieldFullTypeName.split("[.]");
                    String fieldTypeName = splits[splits.length - 1];
                    beanId = Character.toLowerCase(fieldTypeName.charAt(0)) + fieldTypeName.substring(1);
                }
                RuntimeBeanReference beanReference = new RuntimeBeanReference(beanId);
                propertyValues.add(fieldName, beanReference);
            }
            //if non of above, do nothing about the field
        }

        return propertyValues;
    }

    private boolean requireTypeConversion(Class<?> fieldType, String valueString) {
        return !fieldType.equals(String.class);
    }


    private String parseBeanId(Class clazz) {
        String beanId;
        Service annotation = (Service) clazz.getAnnotation(Service.class);
        beanId = annotation.beanId();
        if (beanId == null || beanId.isEmpty()) {
            String splits[] = clazz.getName().split("[.]");
            String className = splits[splits.length - 1];
            beanId = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        }
        return beanId;
    }

    /**
     * check if class c is annotated with, @Service, @Component, @Bean ...
     * here only implement @Service for demonstration purpose
     */
    private boolean isAnnotatedBeanClass(Class c) {
        return c.isAnnotationPresent(Service.class);
    }

    private List<Class> getClasses(String packageName) {
        ClassLoader cl = ClassUtils.getClassLoader();
        String searchDir = packageName.replaceAll("[.]", "/") + "/";
        InputStream is = cl.getResourceAsStream(searchDir);
        System.out.println(is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Class> list = reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toList());
        return list;
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }


}
