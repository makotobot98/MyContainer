package com.mako.beans.definition.namespaceHandler;


import com.mako.beans.definition.BeanDefinition;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.Set;

public class ComponentScanNamespaceHandler implements NamespaceHandler {
    private final String BASE_PACKAGE = "base-package";

    @Override
    public void init() {}

    /**
     * parse a xml tag of <component-scan base-package=".."></component-scan>
     * 1. load all annotated resources under the base-package into java file URL
     * 2. convert all annotated beans into bean definition
     *  - convert URL into a class<?> (Through {@URLClassLoader})
     *  - convert a class definition into bean definition
     * 3. store all parsed bean definitions through BeanDefinitionRegistry
     * @param e xml dom4j's element <component-scan></component-scan>
     * @param parserContext parser context that encapsulates the configuration needed to parse
     */
    @Override
    public void parse(Element e, ParserContext parserContext) {
        String locationPath = e.attributeValue(BASE_PACKAGE);

    }


}
