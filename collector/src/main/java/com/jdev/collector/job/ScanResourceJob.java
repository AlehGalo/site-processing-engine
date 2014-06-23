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

import com.jdev.collector.job.handler.IExceptionalCaseHandler;
import com.jdev.collector.job.strategy.DefaultCongregationStrategy;
import com.jdev.collector.job.strategy.ICongragationFlowStrategy;
import com.jdev.collector.job.validator.CommonEntityValidator;
import com.jdev.collector.job.validator.IValidator;
import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.factory.ICollectorBuilder;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
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
    private static final String EMTPY_ERROR_MESSAGE = "Empty Error Message";

    /**
     * 
     */
    private final int MAX_DATABASE_ERROR_QUEUE_COUNT = 10;

    /**
     * 
     */
    private final IUnitOfWork unitOfWork;

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
    private CommonEntityValidator entityValidator;

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
    private final ICollectorBuilder collectorBuilder;

    /**
     * @param collectorBuilder
     * @param unitOfWork
     */
    public ScanResourceJob(ICollectorBuilder collectorBuilder, final IUnitOfWork unitOfWork) {
        Assert.notNull(collectorBuilder);
        Assert.notNull(unitOfWork);
        this.collectorBuilder = collectorBuilder;
        entityValidator = new CommonEntityValidator();
        strategy = new DefaultCongregationStrategy();
        this.unitOfWork = unitOfWork;
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 100)
    public void scan() {
        AbstractCollector collector = collectorBuilder.createCollector();
        System.out.println(collector);
        collector.setEventHandlerDelegate(this);
        job = createInitiatedJob();
        System.out.println(collectorBuilder.getCredential());
        job.setCredential(collectorBuilder.getCredential());
        unitOfWork.saveJob(job);
        try {
            collector.congregate();
        } catch (CrawlerException ce) {
            saveCrawlerError(ce);
            return;
        } catch (RuntimeException re) {
            re.printStackTrace();
            // TODO: change it
        }
        job.setStatus(JobStatusEnum.FINISHED);
        finishExecutionWell();
    }

    /**
     * @param ce
     *            Exception.
     */
    private void saveCrawlerError(final CrawlerException ce) {
        CrawlerError crawlerError = new CrawlerError();
        PersistentError error = crawlerError.getError();
        setErrorMessage(error, ce.getMessage());
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
        if (saveArticle(article)) {
            dbQueueErrorsCounter = 0;
        } else {
            ++dbQueueErrorsCounter;
        }
        checkDBErrors();
        if (!strategy.isCongregationContinued()) {
            // TODO: remove this ugly code.
            processError(new IllegalArgumentException(), url);
        }
    }

    /**
     * @param article
     */
    private boolean saveArticle(final Article article) {
        ++transactionCounter;
        boolean saveArticle = unitOfWork.saveArticle(article);
        if (validateArticle(article) && saveArticle) {
            if (transactionCounter >= 10) {
                updateJobEndTime();
                unitOfWork.updateJob(job);
                transactionCounter = 0;
            }
            return true;
        }
        return false;
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
            setErrorMessage(perError, e.getMessage());
            error.setUrl(resourceUrl);
            perError.setJob(job);
            unitOfWork.saveDatabaseError(error);
            ++dbQueueErrorsCounter;
            checkDBErrors();
        } else {
            ReflectionUtils.rethrowRuntimeException(e);
        }
    }

    private void checkDBErrors() {
        if (dbQueueErrorsCounter >= MAX_DATABASE_ERROR_QUEUE_COUNT) {
            updateJobEndTime();
            job.setStatus(JobStatusEnum.DB_ERRORS_MUCH);
            unitOfWork.updateJob(job);
            throw new RuntimeException();
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
        if (entityValidator != null) {
            return entityValidator.validate(article);
        }
        throw new UnsupportedOperationException("Validator is null");
    }

    /**
     * @return the entityValidator
     */
    public final IValidator getEntityValidator() {
        return entityValidator;
    }

    /**
     * @param entityValidator
     *            the entityValidator to set
     */
    public final void setEntityValidator(final CommonEntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    /**
     * @param error
     * @param errorMessage
     */
    private void setErrorMessage(final PersistentError error, final String errorMessage) {
        String res = errorMessage;
        if (StringUtils.isEmpty(errorMessage)) {
            res = EMTPY_ERROR_MESSAGE;
        }
        error.setError(res);
    }
}
