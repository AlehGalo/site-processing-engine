/**
 *
 */
package com.jdev.crawler.core.request;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 */
public interface IRequestBuilder {

    /**
     * @param method
     * @param url
     * @param params
     * @return
     * @throws SelectionException
     */
    HttpRequestBase buildRequest(final HTTPMethod method, final String url,
            final List<ISelectorResult> params) throws SelectionException;
}
