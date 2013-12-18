/**
 * 
 */
package com.jdev.extractor.extractor;

import org.springframework.util.Assert;

import com.jdev.domain.Information;
import com.jdev.domain.adapter.IContent;
import com.jdev.extractor.analyser.AbstractAnalyser;

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
     * com.jdev.extractor.analyser.IAnalyse#analyse(com.jdev.extractor.spider.core.url
     * .IContent, com.jdev.extractor.domain.Information)
     */
    @Override
    public void analyse(final IContent url, final Information ii) {
        if (url != null && ii != null) {
            // pusher.pushData(null, e);
            // extractor.extract(url);
        }
    }
}
