package com.mako.beans.definition.namespaceHandler;


import com.mako.beans.definition.BeanDefinition;
import com.mako.beans.definition.BeanDefinitionRegistry;
import com.mako.io.Resource;
import com.mako.utils.ClassUtils;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void parse(Element e, ParserContext parserContext) {
        String packageName = e.attributeValue(BASE_PACKAGE);
        List<Class> candidateClasses = getClasses(packageName);
        for (Class c : candidateClasses) {
            //load the bean definition of annotated class
            if (isAnnotatedBeanClass(c)) {
                loadBeanDefinition(c, parserContext.getBeanDefinitionRegistry());
            }
        }
    }

    private void loadBeanDefinition(Class c, BeanDefinitionRegistry beanDefinitionRegistry) {

    }

    private boolean isAnnotatedBeanClass(Class c) {
        return false;
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
