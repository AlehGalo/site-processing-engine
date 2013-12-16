/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;

/**
 * @author Aleh
 */
public class StaticTextSelector implements ISelector {

    /**
     * Logger.
     */
    private static Logger LOGGER = LoggerFactory
	    .getLogger(StaticTextSelector.class);

    /**
     * 
     */
    private ISelectorResult result;

    public StaticTextSelector(final String name, final String value) {
	result = new SelectorResult(name, value);
    }

    @Override
    public Collection<ISelectorResult> selectValues(final Object content) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("[StaticTextSelector] >> {} {}", result.getName(),
		    result.getValue());
	}
	return Collections.singletonList(result);
    }
}