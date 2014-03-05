/**
 * 
 */
package com.jdev.crawler.core.evaluator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class JSoupEvaluator implements IEvaluator<Elements> {

    /**
     * JSoup selector.
     */
    private final String selector;

    /**
     * JSoup document.
     */
    private final Document doc;

    /**
     * 
     */
    public JSoupEvaluator(final String selector, final Document document) {
        Assert.hasLength(selector);
        Assert.notNull(document);
        this.selector = selector;
        doc = document;
    }

    /**
     * @param selector
     *            jsoup selector.
     * @param document
     *            html document.
     */
    public JSoupEvaluator(final String selector, final String document) {
        this(selector, Jsoup.parse(document));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.content.IEvaluator#evaluate()
     */
    @Override
    public Elements evaluate() {
        return doc.select(selector);
    }

}
