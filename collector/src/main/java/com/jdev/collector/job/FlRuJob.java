/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;

import com.jdev.collector.site.FlRuCollector;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Credential;

/**
 * @author Aleh
 * 
 */
public class FlRuJob extends AbstractScanResourceJob {

    /**
     * @param credential
     *            credentials.
     */
    public FlRuJob(final Credential credential) {
        super(new FlRuCollector(createUserData(credential)));
    }

    /**
     * @throws CrawlerException
     */
    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void doTheJob() throws CrawlerException {
        scan();
    }
}