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
    private final IProcess flowProcess;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.ICrawler#collect()
     */
    @Override
    public void collect() throws CrawlerException {
        try {
            final ProcessSession session = new ProcessSession(this);
            session.putValue(RequestReservedWord.UUID.getWord(), userData.getUniqueKey());
            flowProcess.process(session, null, new DefaultSelectorExtractStrategy());
        } catch (CrawlerException ce) {
            throw ce;
        } finally {
            client.getConnectionManager().shutdown();
        }
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

    public void setStore(final CookieStore store) {
        this.cookieStorage = store;
    }

    public final void setRequestBuilder(final IRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public void setClient(final HttpClient client) {
        this.client = client;
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

    public final void setFileStorage(final IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public ISettings getSettings() {
        return settings;
    }

    @Override
    public HttpClient getHttpClient() {
        return this.client;
    }

    @Override
    public void stop() {
        // TODO: implement it.
    }
}