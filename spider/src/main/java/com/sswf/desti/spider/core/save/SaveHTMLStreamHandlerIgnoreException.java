/**
 * 
 */
package com.sswf.desti.spider.core.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.handler.SaveHTMLStreamHandler;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class SaveHTMLStreamHandlerIgnoreException extends SaveHTMLStreamHandler {

    /**
     * Logger.
     */
    private final static Logger LOGGER = LoggerFactory
            .getLogger(SaveHTMLStreamHandlerIgnoreException.class);

    @Override
    public void handle(final IProcessSession session, final byte[] content) throws CrawlerException {
        try {
            super.handle(session, content);
        } catch (final CrawlerException e) {
            LOGGER.debug(e.getMessage());
        }
    }

}
