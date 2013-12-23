/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.HOST;

/**
 * @author Aleh
 */
public class StaticTextHostSelector extends StaticTextSelector {

    /**
     * @param value
     *            host value.
     */
    public StaticTextHostSelector(final String value) {
        super(HOST, value);
    }
}
