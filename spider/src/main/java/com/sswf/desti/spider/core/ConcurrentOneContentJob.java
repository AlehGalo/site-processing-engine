/**
 * 
 */
package com.sswf.desti.spider.core;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdev.crawler.observer.IObserverable;
import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.domain.statistics.Statistics;
import com.sswf.desti.spider.core.coordinator.IURLCoordinator;

/**
 * @author Aleh
 * 
 */
class ConcurrentOneContentJob implements Runnable {

    /**
     * Job.
     */
    private final OneContentJob job;

    /**
     * 
     */
    private IContent url;

    /**
     * 
     */
    public ConcurrentOneContentJob(final Statistics stat, final AtomicInteger pagesScanned,
            final String host, final IObserverable<IContent> obs,
            final IURLCoordinator<IContent> urlCoordinator, final Set<IContent> setOfNotDomainedUrls) {
        job = new OneContentJob(stat, pagesScanned, host, obs, urlCoordinator, setOfNotDomainedUrls);
    }

    @Override
    public void run() {
        job.atomicContentExecution(url);
    }

    /**
     * @param url
     *            the url to set
     */
    final void setUrl(final IContent url) {
        this.url = url;
    }

}
