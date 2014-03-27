/**
 *
 */
package com.jdev.crawler.core.step;

import java.util.Collection;

import com.jdev.crawler.core.selector.ISelector;

/**
 * @author Aleh
 */
public interface IStepConfig {

    /**
     * @return of processing.
     */
    String getUrl();

    /**
     * @return selectors which results should be added into the request.
     */
    Collection<ISelector<?>> getParameters();

    /**
     * @return POST, PUT, GET and so on.
     * @see HTTPMethod
     */
    HTTPMethod getMethod();
}
