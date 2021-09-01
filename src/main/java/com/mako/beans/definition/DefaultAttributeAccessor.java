package com.mako.beans.definition;

public class DefaultAttributeAccessor implements AttributeAccessor {

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public boolean hasAttribute(String name) {
        return false;
    }

    @Override
    public String[] attributeNames() {
        return null;
    }
}
