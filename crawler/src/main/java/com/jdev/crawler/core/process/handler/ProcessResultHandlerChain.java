/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class ProcessResultHandlerChain implements IProcessResultHandler {

    /**
     * List of handlers to be processed in handling.
     */
    private final List<IProcessResultHandler> listOfHandlers;

    /**
     * @param listOfHandlers
     */
    public ProcessResultHandlerChain(final List<IProcessResultHandler> listOfHandlers) {
        this.listOfHandlers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listOfHandlers)) {
            this.listOfHandlers.addAll(listOfHandlers);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IProcessResultHandler#handle(com.jdev.crawler
     * .core.process.IProcessSession,
     * com.jdev.crawler.core.process.model.IEntity)
     */
    @Override
    public void handle(final IProcessSession session, final IEntity entity) throws CrawlerException {
        for (IProcessResultHandler handler : listOfHandlers) {
            handler.handle(session, entity);
        }
    }
}
