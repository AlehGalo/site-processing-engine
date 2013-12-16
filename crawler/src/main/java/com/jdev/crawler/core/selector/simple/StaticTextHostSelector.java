/**
 *
 */
package com.jdev.crawler.core.selector.simple;

/**
 * @author Aleh
 */
public class StaticTextHostSelector extends StaticTextSelector {
    public StaticTextHostSelector(final String value) {
	super("${host}", value);
    }
}
