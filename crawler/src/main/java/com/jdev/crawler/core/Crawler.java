package com.jdev.crawler.core;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.ProcessSession;
import com.jdev.crawler.core.process.extract.DefaultSelectorExtractStrategy;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.settings.CrawlerSettings;
import com.jdev.crawler.core.settings.ISettings;
import com.jdev.crawler.core.store.FileStorage;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.util.Assert;

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
    private final ISettings settings;

    /**
     * @param process
     * @param userData
     */
    public Crawler(final IProcess process, final IUserData userData) {
        this.flowProcess = process;
        this.userData = verifyUserData(userData);
        this.fileStorage = new FileStorage(DEFAULT_STORAGE_NAME);
        this.settings = new CrawlerSettings();
    }

    @Override
    public void collect() throws CrawlerException {
        try {
            final ProcessSession session = new ProcessSession(this);
            session.putValue(RequestReservedWord.UUID.getWord(), userData.getUniqueKey());
            flowProcess.process(session, null, new DefaultSelectorExtractStrategy());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    public void setStore(final CookieStore store) {
        this.cookieStorage = store;
    }

    public final void setRequestBuilder(final IRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
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

    /**
     * If there is not user required you have to create at least some dummy
     * user.
     * 
     * @param data
     *            to be validated.
     * @return user data validated.
     */
    private IUserData verifyUserData(final IUserData data) {
        Assert.notNull(data, "User data should be specified");
        Assert.notNull(data.getCompany(), "Company should be specified for user datat");
        return data;
    }

    @Override
    public ISettings getSettings() {
        return settings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessContext#getHttpClient()
     */
    @Override
    public HttpClient getHttpClient() {
        return this.client;
    }

    /**
     * @param client
     *            any implementation of HttpClient.
     */
    public void setClient(final HttpClient client) {
        this.client = client;
    }

}