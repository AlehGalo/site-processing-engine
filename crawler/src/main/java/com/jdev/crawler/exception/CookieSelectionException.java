/**
 *
 */
package com.jdev.crawler.exception;

import static java.lang.String.format;

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
     * @param message
     */
    public CookieSelectionException(final String message) {
        super(message);
    }

    /**
     * @param name
     * @return creation of custom exception.
     */
    public static final CookieSelectionException createFormatted(final String name) {
        return new CookieSelectionException(format(ERROR_FORMAT, name));
    }
}