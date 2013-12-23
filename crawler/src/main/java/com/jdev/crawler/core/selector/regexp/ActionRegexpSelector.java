/**
 * 
 */
package com.jdev.crawler.core.selector.regexp;

import static com.jdev.crawler.core.selector.RequestReservedWord.ACTION;

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
        super(ACTION, selector);
    }
}