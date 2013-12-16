package com.jdev.crawler.core;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.process.DefaultSelectorExtractStrategy;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.ProcessSession;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.store.FileStorage;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 */
public class Crawler implements ICrawler, IProcessContext {

    private static final String DEFAULT_STORAGE_NAME = "crawler_storage";

    private HttpClient client;
    private IRequestBuilder requestBuilder;

    private IFileStorage fileStorage;
    private CookieStore cookieStorage;

    private final IUserData userData;

    private IProcess flowProcess;


    private int repeatTime = 5;
    private int waitInterval = 10000;

    /**
     * By default all html files are save at a hard drive.
     */
    private boolean storeMarkup = true;


    /**
     * @param process
     * @param userData
     */
    public Crawler(final IProcess process, final IUserData userData) {
        this.flowProcess = process;
        this.userData = userData;
        this.fileStorage = new FileStorage(DEFAULT_STORAGE_NAME);
    }

    @Override
    public void collect() throws CrawlerException {
        try {
            final ProcessSession session = new ProcessSession(this);
            session.putValue(RequestReservedWord.PHONE.getWord(), userData.getUniqueKey());
            flowProcess.process(session, null, new DefaultSelectorExtractStrategy());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    public void setClient(final HttpClient client) {
        this.client = client;
    }

    public void setStore(final CookieStore store) {
        this.cookieStorage = store;
    }

    public final void setRequestBuilder(final IRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public final void setWaitInterval(final int waitInterval) {
        this.waitInterval = waitInterval;
    }

    public void setRepeatTime(final int repeatTime) {
        this.repeatTime = repeatTime;
    }

    public void setStoreMarkup(final boolean storeMarkup) {
        this.storeMarkup = storeMarkup;
    }

    @Override
    public HttpClient getClient() {
        return client;
    }

    @Override
    public IRequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    @Override
    public CookieStore getCookieStore() {
        return cookieStorage;
    }

    @Override
    public IFileStorage getFileStorage() {
        return fileStorage;
    }

    @Override
    public int getRepeatTime() {
        return repeatTime;
    }

    @Override
    public int getWaitInterval() {
        return waitInterval;
    }

    @Override
    public boolean isStoreMarkup() {
        return storeMarkup;
    }

    @Override
    public IUserData getUserData() {
        return userData;
    }

    @Override
    public void setProcess(final IProcess process) {
        this.flowProcess = process;
    }

    public final void setFileStorage(final IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }
}