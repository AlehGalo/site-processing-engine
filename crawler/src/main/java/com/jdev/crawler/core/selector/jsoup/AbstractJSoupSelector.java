/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractJSoupSelector<T> implements ISelector {

    /**
     * Logger for the main actions.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJSoupSelector.class);

    /**
     * JSoup document. To be analysed.
     */
    // private final Document doc;

    /**
     * JSoup css selector selector style.
     */
    private final String selector;

    /**
     * @param document
     *            JSoup document.
     * @param selector
     *            jsoup selector.
     */
    AbstractJSoupSelector(
    // final Document document,
            final String selector) {
        // Assert.notNull(document);
        Assert.hasLength(selector);
        // this.doc = document;
        this.selector = selector;
    }

    // /**
    // * @param htmlContent
    // * Html document as string.
    // * @param selector
    // * jsoup selector.
    // */
    // AbstractJSoupSelector(final String htmlContent, final String selector) {
    // this(Jsoup.parse(htmlContent), selector);
    // }

    /**
     * @return object.
     */
    @SuppressWarnings("unchecked")
    protected final T commonEvaluate() {
        return null;
        // try {
        // Elements elements = doc.select(selector);
        // if (elements.isEmpty()){
        //
        // } else {
        // elements.get(0).
        // }
        // } catch (XPathExpressionException ex) {
        // LOGGER.error(ex.getMessage());
        // return null;
        // }
    }
}
