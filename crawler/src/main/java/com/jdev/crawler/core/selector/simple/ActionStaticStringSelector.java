/**
 * 
 */
package com.jdev.crawler.core.selector.simple;

import com.jdev.crawler.core.selector.RequestReservedWord;

/**
 * @author Aleh
 * 
 */
public class ActionStaticStringSelector extends StaticStringSelector {

    /**
     * @param word
     * @param value
     */
    public ActionStaticStringSelector(final String value) {
        super(RequestReservedWord.ACTION, value);
    }

}
