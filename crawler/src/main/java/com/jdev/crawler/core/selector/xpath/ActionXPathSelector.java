/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import static com.jdev.crawler.core.selector.RequestReservedWord.ACTION;

/**
 * @author Aleh
 */
public class ActionXPathSelector extends EnumXPathSelector {

    /**
     * @param selector
     *            xpath expression
     */
    public ActionXPathSelector(final String selector) {
        super(ACTION, selector);
    }
}
