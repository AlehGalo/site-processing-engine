/**
 * 
 */
package com.jdev.collector.job;

import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.Job;

/**
 * @author Aleh
 * 
 */
public interface IUnitOfWork {

    /**
     * @param article
     */
    boolean saveArticle(Article article);

    /**
     * @param job
     */
    void saveJob(Job job);

    /**
     * @param job
     */
    void updateJob(Job job);
}