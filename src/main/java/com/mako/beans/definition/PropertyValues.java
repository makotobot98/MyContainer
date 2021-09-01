package com.mako.beans.definition;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PropertyValues extends Iterable<PropertyValue> {
    /**
     * Return an {@link Iterator} over the property values.
     * @since 5.1
     */
    @Override
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(getPropertyValues()).iterator();
    }

    /**
     * Return a {@link Spliterator} over the property values.
     * @since 5.1
     */
    @Override
    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(getPropertyValues(), 0);
    }

    /**
     * Return a sequential {@link Stream} containing the property values.
     */
    default Stream<PropertyValue> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    PropertyValue[] getPropertyValues();

    /**
     * Return the property value with the given name, if any.
     * @param propertyName the name to search for
     * @return the property value, or {@code null} if none
     */
    PropertyValue getPropertyValue(String propertyName);

    /**
     * Is there a property value (or other processing entry) for this property?
     * @param propertyName the name of the property we're interested in
     * @return whether there is a property value for this property
     */
    boolean contains(String propertyName);

    /**
     * Does this holder not contain any PropertyValue objects at all?
     */
    boolean isEmpty();
}
