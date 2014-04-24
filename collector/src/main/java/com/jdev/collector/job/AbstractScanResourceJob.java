/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

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

    // /**
    // *
    // */
    // private IExceptionalCaseHandler<Exception> handler;

    /**
     * 
     */
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

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
        // setHandler(new StopAtFirstErrorExceptionalHandler<Exception>());
    }

    /**
     * Do scheduled taks. Spring bug if annotation is placed with @Override
     * annotation.
     */
    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    private void doJobScheduled() {
        scan();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.job.IScanResourceJob#scan()
     */
    @Override
    public void scan() {
        job = createInitiatedJob();
        unitOfWork.saveJob(job);
        boolean error = false;
        try {
            collector.congregate();
        } catch (CrawlerException e) {
            processError(e);
            error = true;
        }
        if (!error) {
            job.setEndTime(new Date());
            job.setStatus("FINISHED");
            unitOfWork.updateJob(job);
        }
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
        job.setStatus("STARTED");
        return job;
    }

    @Override
    public void articleCollected(final Article article) {
        article.setJob(job);
        try {
            unitOfWork.saveArticle(article);
        } catch (Exception e) {
            processError(e);
        }
    }

    /**
     * @param e
     */
    private void processError(final Exception e) {
        job.setEndTime(new Date());
        job.setReasonOfStopping(e.getMessage());
        job.setStatus("ERROR");
        unitOfWork.updateJob(job);
        ReflectionUtils.rethrowRuntimeException(e);
    }

    // /**
    // * @return the handler
    // */
    // public final IExceptionalCaseHandler<Exception> getHandler() {
    // return handler;
    // }
    //
    // /**
    // * @param handler
    // * the handler to set
    // */
    // public final void setHandler(final IExceptionalCaseHandler<Exception>
    // handler) {
    // this.handler = handler;
    // }
}
