package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.process.extract.DefaultSelectorExtractStrategy;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

public class ProcessChain implements IProcess {

    private final List<? extends IProcess> elements;

    public ProcessChain(final List<? extends IProcess> elements) {
        this.elements = elements;
    }

    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        IEntity c = content;
        for (int i = 0; i < elements.size(); i++) {
            final IProcess element = elements.get(i);
            c = element.process(session, c, i == 0 ? selectorExtractStrategy
                    : new DefaultSelectorExtractStrategy());
        }
        return c;
    }
}
