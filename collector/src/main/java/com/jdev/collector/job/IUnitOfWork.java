/**
 * 
 */
package com.jdev.collector.job;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Job;

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
     * @param job
     */
    void saveJob(Job job);

    /**
     * @param job
     */
    void updateJob(Job job);
}