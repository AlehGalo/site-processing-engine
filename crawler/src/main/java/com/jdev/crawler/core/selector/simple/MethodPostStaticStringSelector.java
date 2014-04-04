/**
 *
 */
package com.jdev.crawler.core.selector.simple;

import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;

import org.jsoup.Connection;

/**
 * @author Aleh
 */
public class MethodPostStaticStringSelector extends StaticStringSelector {

    /**
     * Post text.
     */
    public MethodPostStaticStringSelector() {
        super(METHOD, Connection.Method.POST.name());
    }
}
