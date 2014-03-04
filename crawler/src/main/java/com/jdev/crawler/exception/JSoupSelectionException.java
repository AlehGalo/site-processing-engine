/**
 * 
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 * 
 */
public class JSoupSelectionException extends SelectionException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param key
     *            selector.
     * @param value
     *            selector.
     */
    public JSoupSelectionException(final String key, final String value) {
        super(key, value);
    }

    /**
     * @param message
     *            error message.
     */
    public JSoupSelectionException(final String message) {
        super(message);
    }

}
