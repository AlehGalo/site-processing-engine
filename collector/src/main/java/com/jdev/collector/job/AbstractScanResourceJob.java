/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
abstract class AbstractScanResourceJob implements IScanResourceJob, IObserver {

    /**
     * 
     */
    @Autowired
    private IUnitOfWork unitOfWork;

    /**
     * 
     */
    private final ICollector collector;

    /**
     * 
     */
    private Job job;

    /**
     * @param userName
     */
    public AbstractScanResourceJob(final AbstractCollector collector) {
        Assert.notNull(collector);
        this.collector = collector;
        collector.setEventHandlerDelegate(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.job.IScanResourceJob#scan()
     */
    @Override
    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void scan() {
        job = createInitiatedJob();
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException e) {
            e.printStackTrace();
            job.setReasonOfStopping(e.getMessage());
        }
        job.setEndTime(new Date());
        unitOfWork.updateJob(job);
    }

    /**
     * @return instantiated job.
     */
    private Job createInitiatedJob() {
        Job job = new Job();
        Date date = new Date();
        job.setStartTime(date);
        job.setEndTime(date);
        job.setReasonOfStopping("NONE");
        return job;
    }

    @Override
    public void articleCollected(final Article article) {
        article.setJob(job);
        try {
            unitOfWork.saveArticle(article);
        } catch (Exception e) {
            job.setEndTime(new Date());
            job.setReasonOfStopping(e.getMessage());
            unitOfWork.updateJob(job);
        }
    }
}
