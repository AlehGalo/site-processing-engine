/**
 * 
 */
package com.jdev.crawler.core.process.container;

import java.util.List;

import com.jdev.crawler.core.process.IConditionalProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class ProcessDoWhile<T extends IConditionalProcess> extends ProcessWhile<T> {

    /**
     * @param elements
     */
    public ProcessDoWhile(final List<T> elements) {
        super(elements);
    }

    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        IEntity c = content;
        T element = getElements().get(0);
        do {
            c = element.process(session, c, extractStrategy);
        } while (element.match(c));
        return c;
    }

}
