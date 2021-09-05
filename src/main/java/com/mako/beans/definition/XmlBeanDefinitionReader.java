package com.mako.beans.definition;

import com.mako.beans.definition.namespaceHandler.CustomNamespaceHandlerFactory;
import com.mako.beans.definition.namespaceHandler.NamespaceHandler;
import com.mako.beans.definition.namespaceHandler.ParserContext;
import com.mako.beans.definition.utils.XmlBeanDefinitionParserDelegate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * subclass of {@AbstractBeanDefinitionReader}, XmlBeanDefinitionReader is delegated to read bean definitions from
 * xml based files
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    private static final String BEAN_CLASS_NAME = "class";
    private static final String BEAN_ID = "id";
    private static final String BEAN_LAZY_INIT_ATTR = "lazyInit";
    private static final String BEAN_SINGLETON_ATTR = "singleton";

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream is = getResourceLoader().getResourceAsStream(location);
        Document document = new SAXReader().read(is);
        parseXmlDocument(document, getBeanDefinitionRegistry());
    }

    private void parseXmlDocument(Document document, BeanDefinitionRegistry beanDefinitionRegistry) throws Exception {
        Element rootElement = document.getRootElement();
        parseXmlDocument(rootElement, beanDefinitionRegistry);
    }
    private void parseXmlDocument(Element rootElement, BeanDefinitionRegistry beanDefinitionRegistry) throws Exception {
        List<Element> ls = rootElement.elements();
        System.out.println(ls);

        //for each xml element, if the name(qualified name) == 'bean', parse that bean definition
        //else parse for custom namespace
        for (Element e : ls) {
            String elementName = e.getName();
            if (XmlBeanDefinitionParserDelegate.isBeanDefinition(elementName)) {
                parseBeanDefinition(e);
            } else {
                parseCustomElement(e);
            }
        }
    }

    /**
     * TODO
     * @param e
     */
    private void parseCustomElement(Element e) throws Exception {
        String elementNameString = e.getName();
        NamespaceHandler handler = CustomNamespaceHandlerFactory.getNamespaceHandler(elementNameString);
        handler.init();
        ParserContext parserContext = new ParserContext(getBeanDefinitionRegistry(), getResourceLoader());
        handler.parse(e, parserContext);
    }

    /**
     * parse a xml <bean></bean> tag
     * @param e
     */
    private void parseBeanDefinition(Element e) throws ClassNotFoundException {
        //parse className, class
        String className = parseBeanClassName(e);
        //parse the class
        Class<?> clazz = parseClazz(e);
        //parse bean id
        String beanId = parseBeanId(e);
        //parse attributes(is lazy init, singleton, init-method, destroy-method ... )
        Map<String, Object> attributes = parseBeanAttributes(e);
        //parse properties
            //each property encapsulate in a key-value pair, refer to `PropertyValue` class
            //a bean definition instance should have a field List<PropertyValue> propertyValues;
        PropertyValues propertyValues = parseBeanProperties(e);

        //build a bean definition instance
        BeanDefinition bd = new SimpleBeanDefinition.Builder()
                .className(className)
                .beanId(beanId)
                .attributes(attributes)
                .propertyValues(propertyValues)
                .clazz(clazz)
                .build();

        //register bean definition
        registerBeanDefinition(getBeanDefinitionRegistry(), bd);
    }

    private Class<?> parseClazz(Element e) throws ClassNotFoundException {
        String className = parseBeanClassName(e);
        Class<?> clazz = getResourceLoader().getClassLoader().loadClass(className);
        return clazz;
    }

    /**
     * parse the bean id given an xml bean element, if no id is specified, use the class name instead
     */
    private String parseBeanId(Element e) {
        String beanId = e.attributeValue(BEAN_ID);
        if (beanId == null) {
            String className = parseBeanClassName(e);
            String[] splits = className.split("[.]");
            beanId = splits[splits.length - 1];
        }
        return beanId;
    }

    private void registerBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry, BeanDefinition bd) {
        String beanId = bd.getBeanId();
        beanDefinitionRegistry.registerBeanDefinition(beanId, bd);
    }

    /**
     * parse all the properties of a xml bean element, and encapsulate properties into {@PropertyValues}
     */
    private PropertyValues parseBeanProperties(Element e) {
        Map<String, Object> propertyMap = new HashMap<>();
        List<Element> propertyElements= e.elements("property");
        for (Element property : propertyElements) {
            String propertyName = property.attributeValue("name");
            String propertyValue = property.attributeValue("value");
            if (propertyValue != null) {
                propertyMap.put(propertyName, propertyValue);
            } else {
                //parse bean reference
                String propertyReferenceName = property.attributeValue("ref");
                if (propertyReferenceName != null) {
                    BeanReference beanReference = new RuntimeBeanReference(propertyReferenceName);
                    propertyMap.put(propertyName, beanReference);
                }
            }
        }
        return new MutablePropertyValues(propertyMap);
    }

    /**
     * parse bean attributes like lazyInit, singleton, scope ...
     * @return
     */
    private Map<String, Object> parseBeanAttributes(Element e) {
        Map<String, Object> attributes = new HashMap<>();
        //parse lazyInit
        String lazyInit = e.attributeValue(BEAN_LAZY_INIT_ATTR);
        if (lazyInit != null) {
            attributes.put(lazyInit, lazyInit.equalsIgnoreCase("false") ? Boolean.valueOf(false) :
                    Boolean.valueOf(true));
        }
        // singleton
        String singleton = e.attributeValue(BEAN_SINGLETON_ATTR);
        if (singleton != null) {
            attributes.put(singleton, singleton.equalsIgnoreCase("false") ? Boolean.valueOf(false) :
                    Boolean.valueOf(true));
        }
        return attributes;
    }

    private String parseBeanClassName(Element e) {
        String className = e.attributeValue(BEAN_CLASS_NAME);
        return className;
    }
}
