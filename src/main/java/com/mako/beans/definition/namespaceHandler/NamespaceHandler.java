package com.mako.beans.definition.namespaceHandler;

import org.dom4j.Element;

public interface NamespaceHandler {
    /**
     * initialize the namespace handler
     */
    public void init();

    /**
     * given the xml dom4j element and the parser context, parse the element using namespace handler's specific logic
     */
    public void parse(Element e, ParserContext parserContext) throws Exception;
}
