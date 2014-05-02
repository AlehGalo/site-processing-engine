/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

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
     * 
     */
    private final StringBuilder builder = new StringBuilder();

    /**
     * Counters.
     */
    private int crawlerExceptions = 0;

    /**
     * 
     */
    private int databaseExceptions = 0;

    /**
     * 
     */
    private int transactionCounter = 0;

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
    public void scan() throws CrawlerException {
        job = createInitiatedJob();
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException ce) {
            ++crawlerExceptions;
            updateJobState();
            job.setStatus("FAILED_CRAWLER");
            job.setReasonOfStopping(ce.getCause().getMessage());
            unitOfWork.updateJob(job);
            throw ce;
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
            ++transactionCounter;
            unitOfWork.saveArticle(article);
        } catch (Exception e) {
            processError(e);
        }
    }

    /**
     * @param e
     */
    private void processError(final Exception e) {
        if (e instanceof HibernateException || e instanceof PersistenceException) {
            builder.append(e.getCause().getMessage());
            ++databaseExceptions;
            if (databaseExceptions >= 10) {
                updateJobState();
                job.setStatus("DB_ERRORS_MUCH");
                job.setReasonOfStopping(builder.toString());
                unitOfWork.updateJob(job);
                ReflectionUtils.rethrowRuntimeException(e);
            }
        } else {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        if (transactionCounter >= 10) {
            updateJobState();
            unitOfWork.updateJob(job);
            transactionCounter = 0;
        }
    }

    /**
     * 
     */
    private void updateJobState() {
        job.setCrawlerErrorsCount(crawlerExceptions);
        job.setDatabaseErrorsCount(databaseExceptions);
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
    public final void setExceptionHandler(final IExceptionalCaseHandler<Exception> exceptionHandler) {
        Assert.notNull(exceptionHandler);
        this.exceptionHandler = exceptionHandler;
    }

}
