package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.process.extract.DefaultSelectorExtractStrategy;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.exception.CrawlerException;

public class ProcessChain implements IProcess {
    private final List<? extends IProcess> elements;

    public ProcessChain(List<? extends IProcess> elements) {
        this.elements = elements;
    }

    @Override
    public byte[] process(IProcessSession session, final byte[] content,
            ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        byte[] c = content;
        for (int i = 0; i < elements.size(); i++) {
            final IProcess element = elements.get(i);
            c = element.process(session, c, i == 0 ? selectorExtractStrategy
                    : new DefaultSelectorExtractStrategy());
        }
        return c;
    }
}
