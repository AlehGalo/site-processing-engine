/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;

import org.jsoup.Connection;

/**
 * @author Aleh
 */
public class StaticTextMethodGetSelector extends StaticTextSelector {

    /**
     * Get method.
     */
    public StaticTextMethodGetSelector() {
        super(METHOD, Connection.Method.GET.name());
    }
}
