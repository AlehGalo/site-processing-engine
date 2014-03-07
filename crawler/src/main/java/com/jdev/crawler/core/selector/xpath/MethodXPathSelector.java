/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;

/**
 * @author Aleh
 */
public class MethodXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     * @param name
     */
    public MethodXPathSelector(final String selector) {
        super(METHOD, selector);
    }

}