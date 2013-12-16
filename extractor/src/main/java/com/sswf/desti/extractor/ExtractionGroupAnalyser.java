/**
 * 
 */
package com.sswf.desti.extractor;

import org.springframework.util.Assert;

import com.sswf.desti.analyser.AbstractAnalyser;
import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.adapter.IContent;

/**
 * @author Aleh
 * 
 * @param <T>
 * @param <E>
 */
public class ExtractionGroupAnalyser<T, E extends IContent, B> extends AbstractAnalyser {

    /**
     * Push parameter to the bean.
     */
    private final IPusher<B, E> pusher;

    /**
     * Extract value from the content.
     */
    private final IExtractor<T, E> extractor;

    /**
     * @param pusher
     * @param extractor
     */
    public ExtractionGroupAnalyser(final IPusher<B, E> pusher, final IExtractor<T, E> extractor) {
        Assert.notNull(pusher);
        Assert.notNull(extractor);
        this.pusher = pusher;
        this.extractor = extractor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.analyser.IAnalyse#analyse(com.sswf.desti.spider.core.url
     * .IContent, com.sswf.desti.domain.Information)
     */
    @Override
    public void analyse(final IContent url, final Information ii) {
        if (url != null && ii != null) {
            // pusher.pushData(null, e);
            // extractor.extract(url);
        }
    }
}
