package com.jdev.crawler.core.process.container;

import java.util.List;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public class ProcessParallel<T extends IProcess> extends AbstractBaseProcessContainer<T> {

    /**
     * @param elements
     */
    public ProcessParallel(final List<T> elements) {
        super(elements);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IProcess#process(com.jdev.crawler.core.
     * process.IProcessSession, com.jdev.crawler.core.process.model.IEntity,
     * com.jdev.crawler.core.process.extract.ISelectorExtractStrategy)
     */
    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        List<T> elements = getElements();
        for (IProcess element : elements) {
            element.process(session, content, extractStrategy);
        }
        return null;
    }
}
