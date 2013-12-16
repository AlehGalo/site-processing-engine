/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 */
public class HostXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     *            xpath expression
     */
    public HostXPathSelector(final String selector) {
	super(RequestReservedWord.HOST, selector);
    }
}
