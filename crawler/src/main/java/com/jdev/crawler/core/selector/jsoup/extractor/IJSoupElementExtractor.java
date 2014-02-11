/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup.extractor;

import org.jsoup.nodes.Element;

public interface IJSoupElementExtractor {

    /**
     * @param element
     * @return
     */
    String getValueFromRecord(final Element element);
}
