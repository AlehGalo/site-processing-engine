/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.EmptySelectorResult;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.selector.SelectorResult;

/**
 * @author Aleh
 */
public class StaticTextSelector implements ISelector<Object> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticTextSelector.class);

    /**
     * 
     */
    private final ISelectorResult result;

    /**
     * @param name
     *            of selector.
     * @param value
     *            of selector.
     */
    public StaticTextSelector(final String name, final String value) {
        result = new SelectorResult(name, value);
    }

    /**
     * @param word
     *            reserved word.
     * @param value
     *            value.
     */
    public StaticTextSelector(final RequestReservedWord word, final String value) {
        if (word != null) {
            result = new SelectorResult(word.getWord(), value);
        } else {
            result = new EmptySelectorResult();
        }
    }

    @Override
    public Collection<ISelectorResult> select(final Object content) {
        LOGGER.debug("Selected values {} {}", result.getName(), result.getValue());
        return Collections.singletonList(result);
    }
}