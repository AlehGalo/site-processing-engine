package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.InvalidPageException;

public class ProcessChoice implements IProcess {
    private final List<? extends IConditionalProcess> elements;

    public ProcessChoice(List<? extends IConditionalProcess> elements) {
        this.elements = elements;
    }

    @Override
    public IEntity process(IProcessSession session, final IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        for (IConditionalProcess element : elements) {
            if (element.match(content)) {
                return element.process(session, content, extractStrategy);
            }
        }
        throw new InvalidPageException("Can`t find appropriate processor.");
    }
}
