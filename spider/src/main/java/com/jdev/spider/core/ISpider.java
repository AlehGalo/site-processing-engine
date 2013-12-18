/**
 * 
 */
package com.jdev.spider.core;

import com.jdev.domain.Information;
import com.jdev.domain.statistics.IStatistics;

/**
 * @author Aleh
 * 
 */
public interface ISpider {

    /**
     * @param pagesLimit
     */
    void collect(final int pagesLimit);

    /**
     * Unlimited collect.
     */
    void collect();

    /**
     * @return
     */
    Information getInformation();

    /**
     * @return
     */
    IStatistics getStatistics();
}
