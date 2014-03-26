/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class SessionPopulateHandlerSelectionResult implements IProcessResultHandler {

    /**
     * 
     */
    private final ISelectorResult result;

    /**
     * 
     */
    public SessionPopulateHandlerSelectionResult(final ISelectorResult result) {
        Assert.notNull(result);
        this.result = result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessResultHandler#handle(cinergy
     * .crawler .core.process.IProcessSession, byte[])
     */
    @Override
    public void handle(final IProcessSession session, final IEntity content)
            throws CrawlerException {
        Assert.notNull(session);
        session.putValue(result.getName(), result.getValue());
    }
}