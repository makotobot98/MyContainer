package com.mako.beans.definition.namespaceHandler;

/**
 * factory class that creates tha handler that parse the xml element other than <bean></bean>
 * for example, a <component-scan></component-scan> tag in the bean definition xml configuration file
 * will be handled by ComponentScanHandler
 */
public abstract class CustomNamespaceHandlerFactory {
    /**
     *
     * @param xmlElementName dom4j parsed xml element tag name
     * @return the corresponding namespace handler for that element
     */
    public static NamespaceHandler getNamespaceHandler(String xmlElementName) {
        if (xmlElementName.equalsIgnoreCase("component-scan")) {
            return new ComponentScanNamespaceHandler();
        }
        return null;
    }
}
