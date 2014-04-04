/**
 * 
 */
package com.jdev.crawler.core.selector.simple;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 * 
 */
public class StaticStringSelector extends SimpleStaticSelector<String> {

    /**
     * @param name
     * @param value
     */
    public StaticStringSelector(final String name, final String value) {
        super(name, value);
    }

    /**
     * @param word
     * @param value
     */
    public StaticStringSelector(final RequestReservedWord word, final String value) {
        super(word, value);
    }
}
