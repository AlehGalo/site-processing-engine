/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class StringSourceJSoupSelector extends AbstractJSoupSelector<String> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringSourceJSoupSelector.class);

    /**
     * @param selector
     * @param name
     */
    public StringSourceJSoupSelector(final String selector, final String name) {
        super(selector, name);
    }

    @Override
    public List<ISelectorResult> selectValues(final String content) throws SelectionException {
        List<ISelectorResult> result = new ArrayList<>();
        if (StringUtils.isNotBlank(content)) {
            IJSoupElementExtractor extractor = getExtractorOrThrowExceptionIfNull();
            Elements elements = createDocument(content).select(getSelector());
            if (elements.isEmpty()) {
                LOGGER.debug("No elements found by selector {0}", getSelector());
            } else {
                final String name = getName();
                for (Element element : elements) {
                    String value = extractor.getValueFromRecord(element);
                    if (value != null) {
                        result.add(new SelectorResult(name, value));
                    }
                }
            }
        } else {
            LOGGER.debug("Content is empty");
        }
        return result;
    }

    /**
     * @return element extractor or exception is thrown.
     */
    private IJSoupElementExtractor getExtractorOrThrowExceptionIfNull() {
        IJSoupElementExtractor extractor = getExtractor();
        Assert.notNull(extractor, "Extractor for JSoup should be set");
        return extractor;
    }
}