package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

public class ProcessParallel implements IProcess {
    private final List<? extends IProcess> elements;

    public ProcessParallel(List<? extends IProcess> elements) {
        this.elements = elements;
    }

    @Override
    public IEntity process(IProcessSession session, final IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        for (IProcess element : elements) {
            element.process(session, content, extractStrategy);
        }
        return null;
    }
}
