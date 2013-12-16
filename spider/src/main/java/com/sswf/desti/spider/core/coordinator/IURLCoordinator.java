/**
 * 
 */
package com.sswf.desti.spider.core.coordinator;

import java.util.Collection;

import org.apache.commons.collections.set.UnmodifiableSet;

/**
 * @author Aleh
 * @param <T>
 * 
 */
public interface IURLCoordinator<T> {

    /**
     * @param col
     * 
     */
    <E extends Collection<T>> void addAllUrls(E col);

    /**
     * @return
     */
    UnmodifiableSet getRemainingUrls();

    /**
     * @return
     */
    T next();

}
