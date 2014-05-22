/**
 * 
 */
package com.jdev.collector.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.AgentEnum;
import com.jdev.crawler.core.HttpClientFactory;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public abstract class AbstractCollector implements ICollector {

    /**
     * Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(ICollector.class);

    /**
     * 
     */
    private IProcess process;

    /**
     * Delegate for handling events.
     */
    private IObserver eventHandlerDelegate;

    /**
     * 
     */
    private final IUserData userData;

    /**
     * 
     */
    private ICrawler crawler;

    /**
     * 
     */
    public AbstractCollector(final IUserData userData) {
        Assert.notNull(userData);
        this.userData = userData;
    }

    /**
     * 
     */
    private void initCrawler() {
        if (crawler == null) {
            this.process = createProcess();
            Assert.notNull(process);
            crawler = new CrawlerBuilder(process, userData)
                    .buildClient(HttpClientFactory.createHttpClient(AgentEnum.FIREFOX_USER_AGENT))
                    .buildCookieStore(HttpClientFactory.getCookieStore())
                    .buildRequestBuilder(new BasicRequestBuilder()).getResult();
        }
    }

    /**
     * @return
     */
    abstract IProcess createProcess();

    @Override
    public void congregate() throws CrawlerException {
        initCrawler();
        crawler.collect();
    }

    /**
     * @return the eventHandlerDelegate
     */
    public final IObserver getEventHandlerDelegate() {
        return eventHandlerDelegate;
    }

    /**
     * @param eventHandlerDelegate
     *            the eventHandlerDelegate to set
     */
    public final void setEventHandlerDelegate(final IObserver eventHandlerDelegate) {
        this.eventHandlerDelegate = eventHandlerDelegate;
    }

    /**
     * @return the userData
     */
    final IUserData getUserData() {
        return userData;
    }
}
