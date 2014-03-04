/**
 * 
 */
package com.jdev.crawler.core.selector;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author Aleh
 * 
 */
public class EmptySelectorResult extends SelectorResult {

    /**
     * Empty object.
     */
    public EmptySelectorResult() {
        super(EMPTY, EMPTY);
    }

}
