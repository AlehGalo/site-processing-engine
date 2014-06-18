/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.jdev.collector.job.handler.IExceptionalCaseHandler;
import com.jdev.collector.job.strategy.DefaultCongregationStrategy;
import com.jdev.collector.job.strategy.ICongragationFlowStrategy;
import com.jdev.collector.job.validator.CommonEntityValidator;
import com.jdev.collector.job.validator.IValidator;
import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.JobStatusEnum;
import com.jdev.domain.entity.PersistentError;

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
     * Strategy that defines of processing flow.
     */
    private final ICongragationFlowStrategy strategy;

    /**
     *
     */
    private IExceptionalCaseHandler<Exception> exceptionHandler;

    /**
     * Entity validator.
     */
    private CommonEntityValidator<Article> articleValidator;

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
        articleValidator = new CommonEntityValidator<>();
        strategy = new DefaultCongregationStrategy();
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void scan() {
        job = createInitiatedJob();
        job.setCredential(credential);
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException ce) {
            saveCrawlerError(ce);
            return;
        }
        job.setStatus(JobStatusEnum.FINISHED);
        finishExecutionWell();
    }

    /**
     * @param ce
     *            Exception.
     */
    private void saveCrawlerError(CrawlerException ce) {
        CrawlerError crawlerError = new CrawlerError();
        PersistentError error = crawlerError.getError();
        error.setError(ce.getMessage());
        error.setJob(job);
        updateJobEndTime();
        job.setStatus(JobStatusEnum.FAILED_CRAWLER);
        unitOfWork.updateJob(job);
        unitOfWork.saveCrawlerError(crawlerError);
    }

    /**
     * Just update job details.
     */
    private void finishExecutionWell() {
        job.setStatus(JobStatusEnum.FINISHED);
        updateJobEndTime();
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
        return job;
    }

    @Override
    public void articleCollected(final Article article) {
        final String url = article.getOriginalArticleUrl();
        article.setJob(job);
        try {
            saveArticle(article);
            dbQueueErrorsCounter = 0;
        } catch (Exception e) {
            processError(e, url);
        }
        if (!strategy.isCongregationContinued()) {
            // TODO: remove this ugly code.
            processError(new IllegalArgumentException(), url);
        }
    }

    /**
     * @param article
     */
    private void saveArticle(final Article article) {
        ++transactionCounter;
        if (validateArticle(article) && unitOfWork.saveArticle(article)) {
            if (transactionCounter >= 10) {
                updateJobEndTime();
                unitOfWork.updateJob(job);
                transactionCounter = 0;
            }
        } else {
            processError(new PersistenceException(), article.getOriginalArticleUrl());
        }
    }

    /**
     * @param e
     * @param resourceUrl
     *            url of the resource.
     */
    private void processError(final Exception e, final String resourceUrl) {
        if (e instanceof HibernateException || e instanceof PersistenceException) {
            DatabaseError error = new DatabaseError();
            PersistentError perError = error.getError();
            perError.setError(e.getMessage());
            error.setUrl(resourceUrl);
            perError.setJob(job);
            unitOfWork.saveDatabaseError(error);
            ++dbQueueErrorsCounter;
            if (dbQueueErrorsCounter >= 10) {
                updateJobEndTime();
                job.setStatus(JobStatusEnum.DB_ERRORS_MUCH);
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
    private void updateJobEndTime() {
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
     * @param article
     * @return
     */
    private boolean validateArticle(final Article article) {
        if (articleValidator != null) {
            articleValidator.setEntity(article);
            return articleValidator.validate();
        }
        throw new UnsupportedOperationException("Validator is null");
    }

    /**
     * @return the articleValidator
     */
    public final IValidator getEntityValidator() {
        return articleValidator;
    }

    /**
     * @param articleValidator
     *            the articleValidator to set
     */
    public final void setEntityValidator(final CommonEntityValidator<Article> entityValidator) {
        articleValidator = entityValidator;
    }
}
