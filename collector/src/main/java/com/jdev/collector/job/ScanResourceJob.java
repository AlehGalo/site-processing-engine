/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.jdev.collector.job.strategy.IExceptionalCaseHandler;
import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.Job;

/**
 * @author Aleh
 * 
 */
public class ScanResourceJob implements IObserver {

    /**
     * 
     */
    private final IUnitOfWork unitOfWork;

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

    /**
     * 
     */
    private int dbQueueErrorsCounter = 0;

    /**
     * 
     */
    private Job job;

    /**
     * 
     */
    private final Credential credential;

    /**
     * @param userName
     */
    public ScanResourceJob(final AbstractCollector collector, final Credential credential,
            final IUnitOfWork unitOfWork) {
        Assert.notNull(collector);
        Assert.notNull(credential);
        Assert.notNull(unitOfWork);
        this.collector = collector;
        this.credential = credential;
        this.unitOfWork = unitOfWork;
        collector.setEventHandlerDelegate(this);
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void scan() {
        job = createInitiatedJob();
        job.setCredential(credential);
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException ce) {
            ++crawlerExceptions;
            updateJobState();
            job.setStatus("FAILED_CRAWLER");
            job.setReasonOfStopping(ce.getMessage());
            unitOfWork.updateJob(job);
            return;
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
            saveArticle(article);
            dbQueueErrorsCounter = 0;
        } catch (Exception e) {
            processError(e);
        }
    }

    /**
     * @param article
     */
    private void saveArticle(final Article article) {
        ++transactionCounter;
        if (validateArticle(article) && unitOfWork.saveArticle(article)) {
            if (transactionCounter >= 10) {
                updateJobState();
                unitOfWork.updateJob(job);
                transactionCounter = 0;
            }
        } else {
            processError(new PersistenceException());
        }
    }

    /**
     * @param e
     */
    private void processError(final Exception e) {
        if (e instanceof HibernateException || e instanceof PersistenceException) {
            builder.append(e.getMessage());
            ++databaseExceptions;
            ++dbQueueErrorsCounter;
            if (dbQueueErrorsCounter >= 10) {
                updateJobState();
                job.setStatus("DB_ERRORS_MUCH");
                job.setReasonOfStopping(builder.toString());
                unitOfWork.updateJob(job);
                ReflectionUtils.rethrowRuntimeException(e);
            }
        } else {
            ReflectionUtils.rethrowRuntimeException(e);
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

    private boolean validateArticle(final Article article) {
        return StringUtils.isNotBlank(article.getTitle())
                && StringUtils.isNotBlank(article.getContent())
                && StringUtils.isNotBlank(article.getOriginalArticleUrl());
    }
}
