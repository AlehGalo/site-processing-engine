package com.jdev.crawler.core.process.container;

import com.jdev.crawler.core.process.IConditionalProcess;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.validator.IValidator;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class ConditionalProcess implements IConditionalProcess {

    /**
     * 
     */
    private final IValidator validator;

    /**
     * 
     */
    private final IProcess inner;

    /**
     * @param validator
     * @param inner
     */
    public ConditionalProcess(final IValidator validator, final IProcess inner) {
        this.validator = validator;
        this.inner = inner;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IConditionalProcess#match(com.jdev.crawler
     * .core.process.model.IEntity)
     */
    @Override
    public boolean match(final IEntity content) {
        return validator.validate(content);
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
        return inner.process(session, content, extractStrategy);
    }
}
