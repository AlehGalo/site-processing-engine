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
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class ProcessWhile<T extends IConditionalProcess> extends AbstractBaseProcessContainer<T> {

    /**
     * @param elements
     */
    public ProcessWhile(final List<T> elements) {
        super(elements);
        Assert.isTrue(elements.size() == 1);
    }

    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        IEntity c = content;
        T element = getElements().get(0);
        while (element.match(c)) {
            c = element.process(session, c, extractStrategy);
        }
        return c;
    }

}
