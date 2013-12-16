/**
 *
 */
package com.jdev.crawler.core.selector.simple;

/**
 * @author Aleh
 */
public class StaticTextMethodPostSelector extends StaticTextSelector {
    public StaticTextMethodPostSelector() {
	super("${method}", "post");
    }
}
