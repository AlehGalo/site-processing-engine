/**
 * 
 */
package com.sswf.desti.extractor;

/**
 * @author Aleh
 * @param <T>
 * @param <E>
 */
public interface IExtractor<T, E> {

    /**
     * @param e
     *            content of the page.
     * @return
     */
    T extract(E e);
}
