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
    CrawlerBuilder buildFileStorage(IFileStorage fileStorage);

    /**
     * @param client
     * @return builder.
     */
    CrawlerBuilder buildClient(HttpClient client);

    /**
     * @param requestBuilder
     * @return builder.
     */
    CrawlerBuilder buildRequestBuilder(IRequestBuilder requestBuilder);

    /**
     * @param cookieStore
     *            store.
     * @return crawler builder.
     */
    CrawlerBuilder buildCookieStore(CookieStore cookieStore);

    /**
     * @param storeMarkup
     *            or not.
     * @return crawler builder.
     */
    CrawlerBuilder buildStoreMarkup(boolean storeMarkup);

    /**
     * @return crawler.
     */
    ICrawler getResult();
}
