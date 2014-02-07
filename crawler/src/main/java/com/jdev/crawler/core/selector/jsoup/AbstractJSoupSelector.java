/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jdev.crawler.core.selector.ISelector;
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
     * @param selector
     *            jsoup selector.
     */
    AbstractJSoupSelector(final String selector, final String name) {
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

}
