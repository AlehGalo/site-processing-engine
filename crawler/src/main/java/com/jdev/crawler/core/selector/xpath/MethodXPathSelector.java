/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 */
public class MethodXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     * @param name
     */
    public MethodXPathSelector(final String selector) {
	super(RequestReservedWord.METHOD, selector);
    }

}