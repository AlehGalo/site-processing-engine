/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 */
public class ActionXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     *            xpath expression
     */
    public ActionXPathSelector(final String selector) {
	super(RequestReservedWord.ACTION, selector);
    }
}
