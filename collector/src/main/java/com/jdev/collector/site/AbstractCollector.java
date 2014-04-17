/**
 * 
 */
package com.jdev.collector.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.AgentEnum;
import com.jdev.crawler.core.HttpClientFactory;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractCollector implements ICollector {

    /**
     * Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(ICollector.class);

    /**
     * 
     */
    private IProcess process;

    /**
     * 
     */
    private final UserData userData;

    /**
     * 
     */
    private ICrawler crawler;

    /**
     * 
     */
    public AbstractCollector(final UserData userData) {
        Assert.notNull(userData);
        this.userData = userData;
        initCrawler();
    }

    /**
     * 
     */
    private void initCrawler() {
        if (crawler == null) {
            this.process = initProcess();
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
    abstract IProcess initProcess();

    @Override
    public void congregate() {
        initCrawler();
        try {
            crawler.collect();
        } catch (CrawlerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
