package com.jdev.crawler.core.process.container;

import java.util.List;

import com.jdev.crawler.core.process.IConditionalProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.InvalidPageException;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public class ProcessChoice<T extends IConditionalProcess> extends AbstractBaseProcessContainer<T> {

    /**
     * @param elements
     */
    public ProcessChoice(final List<T> elements) {
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
        for (IConditionalProcess element : elements) {
            if (element.match(content)) {
                return element.process(session, content, extractStrategy);
            }
        }
        throw new InvalidPageException("Can`t find appropriate processor.");
    }
}
