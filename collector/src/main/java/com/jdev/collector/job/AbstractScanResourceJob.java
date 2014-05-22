/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.jdev.collector.job.strategy.IExceptionalCaseHandler;
import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Credential;
import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
@Component
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
    public AbstractScanResourceJob(final AbstractCollector collector, final Credential credential) {
        Assert.notNull(collector);
        Assert.notNull(credential);
        this.collector = collector;
        this.credential = credential;
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
        job.setCredential(this.credential);
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

    /**
     * Constant name.
     */
    private static final String NAME = "Name";

    /**
     * @param credentials
     * @return
     */
    static IUserData createUserData(final Credential credentials) {
        Assert.notNull(credentials);
        final Integer companyId = credentials.getSite().getId().intValue();
        return new UserData(credentials.getUsername(), credentials.getPassword(), new ICompany() {

            @Override
            public String getCompanyName() {
                return NAME + companyId;
            }

            @Override
            public Integer getCompanyId() {
                return companyId;
            }
        });
    }

    private boolean validateArticle(final Article article) {
        return StringUtils.isNotBlank(article.getTitle())
                && StringUtils.isNotBlank(article.getContent())
                && StringUtils.isNotBlank(article.getOriginalArticleUrl());
    }
}
