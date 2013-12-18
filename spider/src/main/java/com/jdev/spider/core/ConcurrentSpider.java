/**
 * 
 */
package com.jdev.spider.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdev.domain.Information;
import com.jdev.domain.adapter.IContent;

/**
 * @author Aleh
 * 
 */
public class ConcurrentSpider extends Spider {

    /**
     * Paged scanned keeper.
     */
    private final AtomicInteger pagesScanned;

    /**
     * Pool size.
     */
    private final int threadPoolSize;

    /**
     * @param inf
     * @param threadPoolSize
     */
    public ConcurrentSpider(final Information inf, final int threadPoolSize) {
        super(inf);
        pagesScanned = new AtomicInteger();
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    public void collect(final int pagesLimit) {
        int counter = 0;
        // getStatistics().setStartTime(System.currentTimeMillis());
        IContent url = getUrlCoordinator().next();
        if (url != null && pagesLimit > counter) {
            ConcurrentOneContentJob job = createJob();
            job.setUrl(url);
            final ExecutorService exService = Executors.newFixedThreadPool(threadPoolSize);
            exService.execute(job);
            exService.shutdown();
            try {
                exService.awaitTermination(1l, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++counter;
        }
        final ExecutorService exService = Executors.newFixedThreadPool(threadPoolSize);
        while (((url = getUrlCoordinator().next()) != null) && pagesLimit > counter) {
            ConcurrentOneContentJob job = createJob();
            job.setUrl(url);
            exService.execute(job);
            ++counter;
        }
        exService.shutdown();
        try {
            exService.awaitTermination(1l, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // getStatistics().setEndTime(System.currentTimeMillis());

    }

    private ConcurrentOneContentJob createJob() {
        return new ConcurrentOneContentJob(statistics, pagesScanned, host, this,
                getUrlCoordinator(), setOfNotDomainedUrls);
    }

    /**
     * @return the pagesScanned
     */
    @Override
    public int getPagesScanned() {
        return pagesScanned.intValue();
    }

}
