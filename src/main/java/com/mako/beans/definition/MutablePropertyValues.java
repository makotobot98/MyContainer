package com.mako.beans.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;

public class MutablePropertyValues implements PropertyValues {
    private final List<PropertyValue> propertyValueList;
    private Set<String> processedProperties;

    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    /**
     * Construct a new MutablePropertyValues object from a Map.
     * @param original a Map with property values keyed by property name Strings
     */
    public MutablePropertyValues(Map<?, ?> original) {
        // We can optimize this because it's all new:
        // There is no replacement of existing property values.
        if (original != null) {
            this.propertyValueList = new ArrayList<>(original.size());
            original.forEach((attrName, attrValue) -> this.propertyValueList.add(
                    new PropertyValue(attrName.toString(), attrValue)));
        }
        else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    /**
     * Return the number of PropertyValue entries in the list.
     */
    public int size() {
        return this.propertyValueList.size();
    }


    /**
     * Add all property values from the given Map.
     * @param other a Map with property values keyed by property name,
     * which must be a String
     * @return this in order to allow for adding multiple property values in a chain
     */
    public MutablePropertyValues addPropertyValues(Map<?, ?> other) {
        if (other != null) {
            other.forEach((attrName, attrValue) -> addPropertyValue(
                    new PropertyValue(attrName.toString(), attrValue)));
        }
        return this;
    }

    /**
     * Add a PropertyValue object, replacing any existing one for the
     * corresponding property or getting merged with it (if applicable).
     * @param propertyName name of the property
     * @param propertyValue value of the property
     * @return this in order to allow for adding multiple property values in a chain
     */
    public MutablePropertyValues add(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
        return this;
    }

    /**
     * Add a PropertyValue object, replacing any existing one for the
     * corresponding property or getting merged with it (if applicable).
     * @param pv the PropertyValue object to add
     * @return this in order to allow for adding multiple property values in a chain
     */
    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                setPropertyValueAt(pv, i);
                return this;
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    /**
     * Modify a PropertyValue object held in this object.
     * Indexed from 0.
     */
    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.set(i, pv);
    }

    /**
     * Register the specified property as "processed" in the sense
     * of some processor calling the corresponding setter method
     * outside of the PropertyValue(s) mechanism.
     * <p>This will lead to {@code true} being returned from
     * a {@link #contains} call for the specified property.
     * @param propertyName the name of the property.
     */
    public void registerProcessedProperty(String propertyName) {
        if (this.processedProperties == null) {
            this.processedProperties = new HashSet<>(4);
        }
        this.processedProperties.add(propertyName);
    }

    /**
     * Clear the "processed" registration of the given property, if any.
     * @since 3.2.13
     */
    public void clearProcessedProperty(String propertyName) {
        if (this.processedProperties != null) {
            this.processedProperties.remove(propertyName);
        }
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
    @Override
    public boolean contains(String propertyName) {
        return (getPropertyValue(propertyName) != null ||
                (this.processedProperties != null && this.processedProperties.contains(propertyName)));
    }

    @Override
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
