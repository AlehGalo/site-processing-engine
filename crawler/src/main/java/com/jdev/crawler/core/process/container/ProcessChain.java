package com.jdev.crawler.core.process.container;

import java.util.List;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.DefaultSelectorExtractStrategy;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public class ProcessChain<T extends IProcess> extends AbstractBaseProcessContainer<T> {

    /**
     * @param elements
     */
    public ProcessChain(final List<T> elements) {
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
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        IEntity c = content;
        List<T> elements = getElements();
        for (int i = 0; i < elements.size(); i++) {
            final IProcess element = elements.get(i);
            c = element.process(session, c, i == 0 ? selectorExtractStrategy
                    : new DefaultSelectorExtractStrategy());
        }
        return c;
    }
}
