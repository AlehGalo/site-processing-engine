/**
 * 
 */
package com.jdev.extractor.extractor;

/**
 * @author Aleh
 * 
 * @param <T>
 * @param <E>
 */
public interface IPusher<T, E> {

    /**
     * Pulling data.
     * 
     * @param t
     * @param e
     */
    void pushData(T t, E e);

}
