/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup.extractor;

import org.jsoup.nodes.Element;

/**
 * @author Aleh
 * 
 */
public interface IJSoupElementExtractor {

    /**
     * @param element
     *            JSoup element.
     * @return String property or value from the element.
     */
    String getValueFromRecord(final Element element);
}
