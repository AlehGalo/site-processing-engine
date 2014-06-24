/**
 * 
 */
package com.jdev.collector.job;

import static com.jdev.collector.job.ErrorMessages.DUPLICATE_ERROR_MESSAGE;
import static com.jdev.collector.job.ErrorMessages.INVALID_ENTITY_MESSAGE;
import static com.jdev.domain.entity.JobStatusEnum.FAILED_CRAWLER;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.jdev.collector.job.exception.EmergencyStopExecutionException;
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
    private int transactionCounter;

    /**
     * 
     */
    private int queueErrorsCounter;

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
    public ScanResourceJob(final ICollectorBuilder collectorBuilder, final IUnitOfWork unitOfWork) {
        Assert.notNull(collectorBuilder);
        Assert.notNull(unitOfWork);
        this.collectorBuilder = collectorBuilder;
        entityValidator = new CommonEntityValidator();
        strategy = new DefaultCongregationStrategy();
        this.unitOfWork = unitOfWork;
    }

    /**
     * 
     */
    @Scheduled(fixedDelay = 600000, initialDelay = 100)
    public void scan() {
        AbstractCollector collector = forceStartValues();
        try {
            collector.congregate();
        } catch (EmergencyStopExecutionException emergencyExit) {
            finishExecution(JobStatusEnum.DB_ERRORS_MUCH);
            return;
        } catch (CrawlerException | RuntimeException exception) {
            saveCrawlerError(exception.getMessage(), FAILED_CRAWLER);
            return;
        }
        finishExecution(JobStatusEnum.FINISHED);
    }

    /**
     * 
     */
    private AbstractCollector forceStartValues() {
        transactionCounter = 0;
        queueErrorsCounter = 0;
        AbstractCollector collector = collectorBuilder.createCollector();
        collector.setEventHandlerDelegate(this);
        job = createInitiatedJob();
        job.setCredential(collectorBuilder.getCredential());
        unitOfWork.saveJob(job);
        return collector;
    }

    /**
     * @param errorMessage
     * @param status
     */
    private void saveCrawlerError(final String errorMessage, final JobStatusEnum status) {
        saveCrawlerError(errorMessage, status, null);
    }

    /**
     * @param errorMessage
     * @param jobStatus
     * @param url
     */
    private void saveCrawlerError(final String errorMessage, final JobStatusEnum jobStatus,
            final String url) {
        final String errorMessageFinal = isEmpty(errorMessage) ? INVALID_ENTITY_MESSAGE
                : errorMessage;
        CrawlerError crawlerError = new CrawlerError();
        PersistentError error = crawlerError.getError();
        crawlerError.setUrl(url);
        error.setError(errorMessageFinal);
        error.setJob(job);
        if (jobStatus != null) {
            finishExecution(jobStatus);
        }
        unitOfWork.saveCrawlerError(crawlerError);
    }

    /**
     * @param e
     * @param resourceUrl
     *            url of the resource.
     */
    private void saveDBError(final String errorMessage, final String resourceUrl) {
        Assert.hasLength(errorMessage);
        Assert.hasLength(resourceUrl);
        DatabaseError error = new DatabaseError();
        PersistentError perError = error.getError();
        perError.setError(errorMessage);
        error.setUrl(resourceUrl);
        perError.setJob(job);
        unitOfWork.saveDatabaseError(error);
    }

    /**
     * Just update job details.
     */
    private void finishExecution(final JobStatusEnum status) {
        Assert.notNull(status);
        job.setStatus(status);
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
    public void articleCollected(final Article article) throws EmergencyStopExecutionException {
        ++transactionCounter;
        saveArticle(article);
        checkErrorsQueue();
        persistsJobState();
        if (!strategy.isCongregationContinued()) {
            // TODO: remove this ugly code.
        }
    }

    /**
     * @param article
     */
    private void saveArticle(final Article article) {
        Assert.notNull(article);
        article.setJob(job);
        boolean validateArticle = validateArticle(article);
        if (validateArticle) {
            boolean isArticleAbsent = unitOfWork.isArticleAbsent(article);
            if (isArticleAbsent) {
                unitOfWork.saveArticle(article);
                queueErrorsCounter = 0;
                return;
            } else {
                saveDBError(DUPLICATE_ERROR_MESSAGE, article.getOriginalArticleUrl());
            }
        } else {
            saveCrawlerError(ErrorMessages.INVALID_ENTITY_MESSAGE, null,
                    article.getOriginalArticleUrl());
        }
        ++queueErrorsCounter;
    }

    /**
     * 
     */
    private void persistsJobState() {
        if (transactionCounter >= 10) {
            updateJobEndTime();
            unitOfWork.updateJob(job);
            transactionCounter = 0;
        }
    }

    /**
     * @throws EmergencyStopExecutionException
     * 
     */
    private void checkErrorsQueue() throws EmergencyStopExecutionException {
        if (queueErrorsCounter >= MAX_DATABASE_ERROR_QUEUE_COUNT) {
            throw new EmergencyStopExecutionException(ErrorMessages.MAXIMUM_EXCEEDED_MESSAGE);
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
}
