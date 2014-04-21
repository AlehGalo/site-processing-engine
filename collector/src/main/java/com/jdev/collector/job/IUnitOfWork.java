/**
 * 
 */
package com.jdev.collector.job;

import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
public interface IUnitOfWork {

    /**
     * @param article
     */
    void saveArticle(Article article);
}
