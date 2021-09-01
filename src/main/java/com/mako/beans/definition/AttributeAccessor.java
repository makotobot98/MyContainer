package com.mako.beans.definition;

public interface AttributeAccessor {
    /**
     * set the attribute of "name" to value "value"
     * @param name name of the attribute
     * @param value value to be set
     */
    void setAttribute(String name, Object value);

    /**
     * get the attribute value given the attribute name
     * @param name name of the attribute
     * @return the value of that attribute
     */
    Object getAttribute(String name);

    /**
     * return if has an attribute of name
     */
    boolean hasAttribute(String name);

    /**
     * return names of all the attributes
     */
    String[] attributeNames();

}
