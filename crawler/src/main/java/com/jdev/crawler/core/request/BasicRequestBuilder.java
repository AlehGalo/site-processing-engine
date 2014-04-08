/**
 *
 */
package com.jdev.crawler.core.request;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.exception.SelectionException;

public class BasicRequestBuilder implements IRequestBuilder {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicRequestBuilder.class);

    /**
     * @param method
     * @param url
     * @return
     * @throws SelectionException
     */
    @Override
    public HttpRequestBase buildRequest(final HTTPMethod method, final String url,
            final List<ISelectorResult> list) throws SelectionException {
        if (method == null || StringUtils.isEmpty(url)) {
            throw new SelectionException(format("Method[%s] or url[%s] cannot be null or empty",
                    method, url));
        }
        HttpRequestBase request = null;
        switch (method) {
        case POST:
            request = buildPostRequest(url, list);
            break;
        case GET:
            request = buildGetRequest(url, list);
            break;
        case DELETE:
        case PUT:
        case HEAD:
        case OPTIONS:
        case TRACE:
            throw new SelectionException(format("Method [%s] is not supported", method));
        }
        debugUrl(request);
        return request;
    }

    /**
     * @param params
     * @return
     */
    protected List<NameValuePair> convertToListValuePair(final HttpParams params) {
        final List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (params instanceof SyncBasicHttpParams) {
            for (final String name : ((SyncBasicHttpParams) params).getNames()) {
                final BasicNameValuePair bvp = new BasicNameValuePair(name,
                        (String) params.getParameter(name));
                list.add(bvp);
            }
        }
        return list;
    }

    /**
     * @param url
     * @param list
     * @return
     */
    protected HttpRequestBase buildGetRequest(final String url, final List<ISelectorResult> list)
            throws SelectionException {
        final HttpGet request = new HttpGet(url);
        request.setParams(convertParams(list));
        return request;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    protected HttpRequestBase buildPostRequest(final String url, final List<ISelectorResult> list)
            throws SelectionException {
        final HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(convertToListValuePair(convertParams(list)),
                Consts.UTF_8));
        return request;
    }

    /**
     * @param list
     * @return
     */
    protected HttpParams convertParams(final List<ISelectorResult> list) {
        final HttpParams params = new SyncBasicHttpParams();
        if (!CollectionUtils.isEmpty(list)) {
            for (final ISelectorResult iSelectorResult : list) {
                params.setParameter(iSelectorResult.getName(), iSelectorResult.getValue());
            }
        }
        return params;
    }

    /**
     * @param request
     */
    protected void debugUrl(final HttpRequestBase request) {
        if (request != null && LOGGER.isDebugEnabled()) {
            LOGGER.debug(request.getURI().toASCIIString());
        }
    }
}
