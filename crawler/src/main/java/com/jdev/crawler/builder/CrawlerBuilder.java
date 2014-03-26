/**
 * 
 */
package com.jdev.crawler.builder;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.Crawler;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh
 * 
 */
public class CrawlerBuilder implements ICrawlerBuilder {

    /**
     * Crawler.
     */
    private final Crawler crawler;

    /**
     * @param process
     *            flow.
     * @param userData
     *            data.
     */
    public CrawlerBuilder(final IProcess process, final IUserData userData) {
        crawler = new Crawler(process, userData);
    }

    @Override
    public ICrawlerBuilder buildFileStorage(final IFileStorage fileStorage) {
        crawler.setFileStorage(fileStorage);
        return this;
    }

    @Override
    public ICrawlerBuilder buildClient(final HttpClient client) {
        crawler.setClient(client);
        return this;
    }

    @Override
    public ICrawlerBuilder buildRequestBuilder(final IRequestBuilder requestBuilder) {
        crawler.setRequestBuilder(requestBuilder);
        return this;
    }

    @Override
    public ICrawlerBuilder buildCookieStore(final CookieStore cookieStore) {
        crawler.setStore(cookieStore);
        return this;
    }

    @Override
    public ICrawler getResult() {
        return crawler;
    }
}
