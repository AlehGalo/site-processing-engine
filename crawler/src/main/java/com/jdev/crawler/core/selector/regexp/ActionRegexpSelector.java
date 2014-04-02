/**
 * 
 */
package com.jdev.crawler.core.selector.regexp;

import static com.jdev.crawler.core.selector.RequestReservedWord.ACTION;

import com.jdev.crawler.core.selector.SelectUnit;

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
        super(new SelectUnit(ACTION.getWord(), selector));
    }
}