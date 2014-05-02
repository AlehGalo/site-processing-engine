/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jdev.collector.job.strategy.IExceptionalCaseHandler;
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
    private IExceptionalCaseHandler<Exception> exceptionHandler;

    /**
     * Counters.
     */
    private final AtomicInteger crawlerExceptions = new AtomicInteger(0),
            databaseExceptions = new AtomicInteger(0);

    /**
     * 
     */
    private final AtomicInteger transactionCounter = new AtomicInteger(0);

    // /**
    // *
    // */
    // @Autowired
    // private ThreadPoolTaskScheduler taskScheduler;

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
    public void scan() {
        job = createInitiatedJob();
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException e) {
            processError(e);
        }
        updateJobState();
        job.setStatus("FINISHED");
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
        // if (!getExceptionHandler().handle(e)) {
        // job.setReasonOfStopping(e.getMessage() + " = " +
        // e.getCause().getMessage());
        // job.setEndTime(new Date());
        // unitOfWork.updateJob(job);
        // ReflectionUtils.rethrowRuntimeException(e);
        // }
        if (e instanceof CrawlerException) {
            crawlerExceptions.incrementAndGet();
            transactionCounter.incrementAndGet();
        }
        if (e instanceof HibernateException) {
            databaseExceptions.incrementAndGet();
            transactionCounter.incrementAndGet();
        }
        if (transactionCounter.intValue() >= 10) {
            updateJobState();
            unitOfWork.updateJob(job);
            transactionCounter.set(0);
        }
    }

    /**
     * 
     */
    private void updateJobState() {
        job.setCrawlerErrorsCount(crawlerExceptions.intValue());
        job.setDatabaseErrorsCount(databaseExceptions.intValue());
        job.setEndTime(new Date());
    }

    /**
     * @return the exceptionHandler
     */
    public final IExceptionalCaseHandler<Exception> getExceptionHandler() {
        return exceptionHandler;
    }

    /**
     * @param exceptionHandler
     *            the exceptionHandler to set
     */
    public final void setExceptionHandler(IExceptionalCaseHandler<Exception> exceptionHandler) {
        Assert.notNull(exceptionHandler);
        this.exceptionHandler = exceptionHandler;
    }
}
