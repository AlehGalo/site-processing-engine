/**
 * 
 */
package com.jdev.collector.job;

import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;

/**
 * @author Aleh
 * 
 */
public interface IUnitOfWork {

    /**
     * @param article
     */
    void saveArticle(Article article);

    /**
     * @param article
     * @return
     */
    boolean isArticleAbsent(Article article);

    /**
     * @param job
     */
    void saveJob(Job job);

    /**
     * @param job
     */
    void updateJob(Job job);

    /**
     * @param error
     *            database error to be stored.
     */
    void saveDatabaseError(DatabaseError error);

    /**
     * @param error
     *            database error to be stored.
     */
    void saveCrawlerError(CrawlerError message);
}