/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class CookieSelectionException extends SelectionException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private static final String ERROR_FORMAT = "Error retrieving cookie with [name=%s]";

    /**
     * @param key
     * @param cause
     */
    public CookieSelectionException(final String key, final Throwable cause) {
        super(String.format(ERROR_FORMAT, key), cause);
    }

    /**
     * @param key
     * @param message
     */
    public CookieSelectionException(final String key) {
        super(String.format(ERROR_FORMAT, key));
    }

    /**
     * @param key
     * @param message
     */
    public CookieSelectionException(final String key, final String message) {
        super(String.format(ERROR_FORMAT, key) + message);
    }

}