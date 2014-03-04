/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class XPathSelectionException extends SelectionException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public XPathSelectionException(final String key, final String value, final Throwable cause) {
        super(key, value, cause);
    }

    /**
     * @param message
     */
    public XPathSelectionException(final String key, final String value) {
        super(key, value);
    }

    /**
     * @param message
     */
    public XPathSelectionException(final String message) {
        super(message);
    }

}