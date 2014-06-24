/**
 * 
 */
package com.jdev.collector.site.handler;

import com.jdev.collector.job.exception.EmergencyStopExecutionException;
import com.jdev.domain.entity.Article;

/**
 * @author Aleh
 * 
 */
public interface IObserver {

    /**
     * @param article
     */
    void articleCollected(final Article article) throws EmergencyStopExecutionException;

}
