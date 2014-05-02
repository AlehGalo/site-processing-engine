/**
 * 
 */
package com.jdev.collector.site.handler;

import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
public interface IObserver {

    /**
     * @param article
     */
    void articleCollected(Article article);

}
