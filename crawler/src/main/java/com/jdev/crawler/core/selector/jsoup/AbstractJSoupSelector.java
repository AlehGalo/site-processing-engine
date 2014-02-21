/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractJSoupSelector<T> implements ISelector<T> {

    /**
     * JSoup css selector selector style.
     */
    private final String selector;

    /**
     * JSoup css selector name.
     */
    private final String name;

    /**
     * Result of JSoup extractor.
     */
    private IJSoupElementExtractor extractor;

    /**
     * @param selector
     *            jsoup selector.
     */
    AbstractJSoupSelector(final String name, final String selector) {
        Assert.hasLength(selector);
        Assert.hasLength(name);
        this.selector = selector;
        this.name = name;
    }

    /**
     * @param htmlCode
     * @return
     */
    protected final static Document createDocument(final String htmlCode) {
        return Jsoup.parse(htmlCode);
    }

    /**
     * @return the selector
     */
    final String getSelector() {
        return selector;
    }

    /**
     * @return the name
     */
    final String getName() {
        return name;
    }

    /**
     * @return the extractor
     */
    public IJSoupElementExtractor getExtractor() {
        return extractor;
    }

    /**
     * @param extractor
     *            the extractor to set
     */
    public void setExtractor(final IJSoupElementExtractor extractor) {
        this.extractor = extractor;
    }

}