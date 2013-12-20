/**
 *
 */
package com.jdev.crawler.core.selector;

import java.util.Collection;

import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 */
public interface ISelector {

    /**
     * @param content
     * @return
     * @throws SelectionException
     */
    Collection<ISelectorResult> selectValues(Object content) throws SelectionException;
}
