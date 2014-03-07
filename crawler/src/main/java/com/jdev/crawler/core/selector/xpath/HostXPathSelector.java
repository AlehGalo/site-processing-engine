/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import static com.jdev.crawler.core.selector.RequestReservedWord.HOST;

/**
 * @author Aleh
 */
public class HostXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     *            xpath expression
     */
    public HostXPathSelector(final String selector) {
        super(HOST, selector);
    }
}
