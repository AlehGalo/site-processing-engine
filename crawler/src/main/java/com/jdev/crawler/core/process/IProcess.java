package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
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
    IEntity process(IProcessSession session, IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException;

}
