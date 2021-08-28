package com.mako.beans.definition.utils;

import java.util.Locale;

public abstract class XmlBeanDefinitionParserDelegate {
    private static final String BEAN_NAME = "BEAN";

    public static boolean isBeanDefinition(String name) {
        return name.equalsIgnoreCase(BEAN_NAME);
    }
}
