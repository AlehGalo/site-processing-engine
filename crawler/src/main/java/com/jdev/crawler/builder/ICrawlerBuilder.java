/**
 * 
 */
package com.jdev.crawler.builder;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.store.IFileStorage;

/**
 * @author Aleh
 * 
 */
public interface ICrawlerBuilder {

    /**
     * @param fileStorage
     *            file storage.
     * @return crawler builder.
     */
    ICrawlerBuilder buildFileStorage(IFileStorage fileStorage);

    /**
     * @param client
     * @return builder.
     */
    ICrawlerBuilder buildClient(HttpClient client);

    /**
     * @param requestBuilder
     * @return builder.
     */
    ICrawlerBuilder buildRequestBuilder(IRequestBuilder requestBuilder);

    /**
     * @param cookieStore
     *            store.
     * @return crawler builder.
     */
    ICrawlerBuilder buildCookieStore(CookieStore cookieStore);

    /**
     * @return crawler.
     */
    ICrawler getResult();
}
