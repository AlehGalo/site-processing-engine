/**
 * 
 */
package com.jdev.collector.job;

import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */

public interface IScanResourceJob {

    /**
     * @throws CrawlerException
     */
    void scan() throws CrawlerException;
}