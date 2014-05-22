/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;

import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Credential;

/**
 * @author Aleh
 * 
 */
public class FreelancerComJob extends AbstractScanResourceJob {

    /**
     * 
     */
    public FreelancerComJob(final Credential credential) {
        super(new FreelancerComCollector(createUserData(credential)), credential);
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void doTheJob() throws CrawlerException {
        scan();
    }
}
