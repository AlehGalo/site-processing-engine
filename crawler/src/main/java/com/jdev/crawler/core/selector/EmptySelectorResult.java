/**
 * 
 */
package com.jdev.crawler.core.selector;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Aleh
 * 
 */
public class EmptySelectorResult extends SelectorResult {

    /**
     * Empty object.
     */
    public EmptySelectorResult() {
        super(StringUtils.EMPTY, StringUtils.EMPTY);
    }

}
