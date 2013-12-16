package com.jdev.crawler.core.process;

import com.jdev.crawler.exception.CrawlerException;

/**
 *
 */
public interface IProcess {

    /**
     * @param session
     * @param content
     * @param extractStrategy
     * @return
     * @throws CrawlerException
     */
    byte[] process(IProcessSession session, byte[] content,
	    ISelectorExtractStrategy extractStrategy) throws CrawlerException;

}
