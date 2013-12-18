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
     *
     */
    private static final String ERROR_FORMAT = "Error extracting xpath: [name=%s] [value=%s]";

    /**
     * @param message
     * @param cause
     */
    public XPathSelectionException(final String key, final String value, final Throwable cause) {
        super(String.format(ERROR_FORMAT, key, value), cause);
    }

    /**
     * @param message
     */
    public XPathSelectionException(final String key, final String value) {
        super(String.format(ERROR_FORMAT, key, value));
    }
}