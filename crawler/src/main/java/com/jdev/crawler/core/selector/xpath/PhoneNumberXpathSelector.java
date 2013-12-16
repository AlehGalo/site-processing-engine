/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 * 
 */
public class PhoneNumberXpathSelector extends EnumXPathSelector {

    /**
     * @param word
     * @param selector
     */
    public PhoneNumberXpathSelector(final String selector) {
	super(RequestReservedWord.PHONE, selector);
    }

}
