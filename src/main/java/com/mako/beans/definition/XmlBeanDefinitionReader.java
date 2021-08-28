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
import java.util.List;

/**
 * subclass of {@AbstractBeanDefinitionReader}, XmlBeanDefinitionReader is delegated to read bean definitions from
 * xml based files
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    @Override
    public void loadBeanDefinitions(String location) throws DocumentException {
        InputStream is = getResourceLoader().getResourceAsStream(location);
        Document document = new SAXReader().read(is);
        parseXmlDocument(document, getBeanDefinitionRegistry());
    }

    private void parseXmlDocument(Document document, BeanDefinitionRegistry beanDefinitionRegistry) {
        Element rootElement = document.getRootElement();
        parseXmlDocument(rootElement, beanDefinitionRegistry);
    }
    private void parseXmlDocument(Element rootElement, BeanDefinitionRegistry beanDefinitionRegistry) {
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
    private void parseCustomElement(Element e) {
        String elementNameString = e.getName();
        NamespaceHandler handler = CustomNamespaceHandlerFactory.getNamespaceHandler(elementNameString);
        handler.init();
        ParserContext parserContext = new ParserContext(getBeanDefinitionRegistry(), getResourceLoader());
        handler.parse(e, parserContext);
    }

    /**
     * TODO
     * @param e
     */
    private void parseBeanDefinition(Element e) {
    }
}
