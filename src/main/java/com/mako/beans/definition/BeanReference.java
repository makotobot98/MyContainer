package com.mako.beans.definition;
/**
 * Interface that exposes a reference to a bean name in an abstract fashion.
 * This interface does not necessarily imply a reference to an actual bean
 * instance; it just expresses a logical reference to the name of a bean.
 */
public interface BeanReference {
    /**
     * Return the target bean name that this reference points to (never {@code null}).
     */
    String getBeanName();
}

