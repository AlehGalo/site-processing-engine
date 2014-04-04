/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;

import org.jsoup.Connection;

/**
 * @author Aleh
 */
public class MethodGetStaticStringSelector extends StaticStringSelector {

    /**
     * Get method.
     */
    public MethodGetStaticStringSelector() {
        super(METHOD, Connection.Method.GET.name());
    }
}
