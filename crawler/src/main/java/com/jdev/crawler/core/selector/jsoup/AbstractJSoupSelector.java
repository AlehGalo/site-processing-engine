/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractJSoupSelector<T> implements ISelector<T> {

    /**
     * Select unit.
     */
    private final ISelectUnit selectUnit;

    /**
     * Result of JSoup extractor.
     */
    private IJSoupElementExtractor extractor;

    /**
     * @param selectUnit
     */
    AbstractJSoupSelector(final ISelectUnit selectUnit) {
        Assert.notNull(selectUnit);
        this.selectUnit = selectUnit;
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

    /**
     * @return the selectUnit
     */
    protected final ISelectUnit getSelectUnit() {
        return selectUnit;
    }
}