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

import com.jdev.crawler.core.evaluator.JSoupEvaluator;
import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.exception.JSoupSelectionException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class StringSourceJSoupSelector extends AbstractJSoupSelector<String> {

    /**
     * Logger for the main actions.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringSourceJSoupSelector.class);

    /**
     * @param selectUnit
     */
    public StringSourceJSoupSelector(final ISelectUnit selectUnit) {
        super(selectUnit);
    }

    @Override
    public List<ISelectorResult> select(final String content) throws SelectionException {
        if (StringUtils.isEmpty(content)) {
            throw new JSoupSelectionException("Selection source cannot be null or empty");
        }
        List<ISelectorResult> result = new ArrayList<>();
        IJSoupElementExtractor extractor = getExtractorOrThrowExceptionIfNull();
        Elements elements = new JSoupEvaluator(getSelectUnit().getSelector(), content).evaluate();
        final String name = getSelectUnit().getName();
        for (Element element : elements) {
            String value = extractor.getValueFromRecord(element);
            if (StringUtils.isBlank(value)) {
                throw new JSoupSelectionException(name, getSelectUnit().getSelector());
            }
            LOGGER.debug("Properties extracted {} {}", name, value);
            result.add(new SelectorResult(name, value));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Results found {}", result.size());
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