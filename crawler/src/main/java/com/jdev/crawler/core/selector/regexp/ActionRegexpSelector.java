/**
 * 
 */
package com.jdev.crawler.core.selector.regexp;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 * 
 */
public class ActionRegexpSelector extends RegexpSelector {

    /**
     * @param name
     * @param selector
     */
    public ActionRegexpSelector(final String selector) {
	super(RequestReservedWord.ACTION, selector);
    }
}