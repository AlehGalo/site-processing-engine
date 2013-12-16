/**
 *
 */
package com.jdev.crawler.core;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 */
public interface ICrawler {

    /**
     * Process collecting.
     * 
     * @param pagesLimit
     *            limit of the pages to be crawled.
     * @throws CrawlerException
     */
    void collect() throws CrawlerException;

    /**
     * @param process
     *            process.
     */
    @Deprecated
    void setProcess(IProcess process);

    /**
     * @return file store.
     */
    IFileStorage getFileStorage();
}
