package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.validator.IValidator;
import com.jdev.crawler.exception.CrawlerException;

public class ConditionalProcess implements IConditionalProcess {
    private final IValidator validator;
    private final IProcess inner;

    public ConditionalProcess(IValidator validator, IProcess inner) {
        this.validator = validator;
        this.inner = inner;
    }

    @Override
    public boolean match(IEntity content) {
        return validator.validate(content);
    }

    @Override
    public IEntity process(IProcessSession session, IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        return inner.process(session, content, extractStrategy);
    }
}
