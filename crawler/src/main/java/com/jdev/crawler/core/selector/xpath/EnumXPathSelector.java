/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.selector.SelectUnit;

/**
 * @author Aleh
 * 
 */
public class EnumXPathSelector extends XPathSelector {

    /**
     * @param word
     * @param selector
     */
    public EnumXPathSelector(final RequestReservedWord word, final String selector) {
        super(new SelectUnit(word.getWord(), selector));
    }

}
