/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;

import org.jsoup.Connection;

/**
 * @author Aleh
 */
public class StaticTextMethodPostSelector extends StaticTextSelector {

    /**
     * Post text.
     */
    public StaticTextMethodPostSelector() {
        super(METHOD, Connection.Method.POST.name());
    }
}
