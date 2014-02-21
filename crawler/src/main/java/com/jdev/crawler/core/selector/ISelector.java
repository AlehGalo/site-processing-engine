/**
 *
 */
package com.jdev.crawler.core.selector;

import java.util.Collection;

import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 */
public interface ISelector<T> {

    /**
     * @param content
     * @return
     * @throws SelectionException
     */
    Collection<ISelectorResult> select(T content) throws SelectionException;
}
