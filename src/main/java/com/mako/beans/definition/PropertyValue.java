package com.mako.beans.definition;

import java.util.Objects;

/**
 * class storing key-value pair property value
 * - note the usage of this in bean definition:
 *      - bean property values will either be a string alue or bean reference
 *      - type conversion will not be performed, meaning a propertyValue will
 *        only store unconverted property value
 */
public class PropertyValue {
    private String name;

    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyValue that = (PropertyValue) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "bean property '" + this.name + "'";
    }
}
