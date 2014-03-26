/**
 *
 */
package com.jdev.crawler.core.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 */
public class UriBasedRequestBuilder extends BasicRequestBuilder {

    @Override
    protected HttpRequestBase buildGetRequest(final String url, final List<ISelectorResult> list)
            throws SelectionException {
        final URIBuilder builder = new URIBuilder();
        builder.setPath(url);
        for (final ISelectorResult iSelectorResult : list) {
            builder.setParameter(iSelectorResult.getName(), iSelectorResult.getValue());
        }
        URI uri;
        try {
            uri = builder.build();
        } catch (final URISyntaxException e) {
            throw new SelectionException("Error building URI", e);
        }
        return new HttpGet(uri);
    }

}
