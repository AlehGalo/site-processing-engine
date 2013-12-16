package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.exception.CrawlerException;

public class ProcessParallel implements IProcess {
    private final List<? extends IProcess> elements;

    public ProcessParallel(List<? extends IProcess> elements) {
        this.elements = elements;
    }

    @Override
    public byte[] process(IProcessSession session, final byte[] content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        for (IProcess element : elements) {
            element.process(session, content, extractStrategy);
        }
        return null;
    }
}
