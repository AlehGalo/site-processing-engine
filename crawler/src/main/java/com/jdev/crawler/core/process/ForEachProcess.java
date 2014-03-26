package com.jdev.crawler.core.process;

import java.util.Collection;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.extract.MergingSelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.CrawlerException;

/**
 * 
 */
public class ForEachProcess implements IProcess {

    private final ISelector<String> cycle;

    private final IProcess inner;

    /**
     * @param cycle
     * @param process
     */
    public ForEachProcess(final ISelector<String> cycle, final IProcess process) {
        this.cycle = cycle;
        inner = process;
    }

    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        final Collection<? extends ISelectorResult> list = cycle.select(new String(content
                .getContent(), content.getCharset()));
        for (final ISelectorResult result : list) {
            inner.process(session, content, new MergingSelectorExtractStrategy(result));
        }
        return null;
    }
}
