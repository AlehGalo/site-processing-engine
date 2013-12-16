/**
 * 
 */
package com.jdev.crawler.core.process;

import com.jdev.crawler.core.request.IRequestBuilder;

/**
 * @author Aleh
 * 
 */
public interface IRequestBuilderContext {

    /**
     * @return Request builder.
     */
    IRequestBuilder getRequestBuilder();

}
