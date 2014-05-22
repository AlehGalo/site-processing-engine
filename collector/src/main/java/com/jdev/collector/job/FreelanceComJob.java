/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;

import com.jdev.collector.site.FreelanceComCollector;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Credential;

/**
 * @author Aleh
 * 
 */
public class FreelanceComJob extends AbstractScanResourceJob {

    /**
     * @param credential
     */
    public FreelanceComJob(final Credential credential) {
        super(new FreelanceComCollector(createUserData(credential)), credential);
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void doTheJob() throws CrawlerException {
        scan();
    }

}