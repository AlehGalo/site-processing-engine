/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.HOST;

/**
 * @author Aleh
 */
public class HostStaticStringSelector extends StaticStringSelector {

    /**
     * @param value
     *            host value.
     */
    public HostStaticStringSelector(final String value) {
        super(HOST, value);
    }
}
