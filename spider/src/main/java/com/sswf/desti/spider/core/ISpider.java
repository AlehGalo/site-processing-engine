/**
 * 
 */
package com.sswf.desti.spider.core;

import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.statistics.IStatistics;

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
