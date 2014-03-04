/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class RegexpSelectionException extends SelectionException {

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param key
     * @param value
     * @param cause
     */
    public RegexpSelectionException(final String key, final String value, final Throwable cause) {
        super(key, value, cause);
    }

    /**
     * @param key
     * @param value
     */
    public RegexpSelectionException(final String key, final String value) {
        super(key, value);
    }

    /**
     * @param message
     */
    public RegexpSelectionException(final String message) {
        super(message);
    }

    /**
     * @param key
     * @param value
     * @param additionalErrorMessage
     */
    public RegexpSelectionException(final String key, final String value,
            final String additionalErrorMessage) {
        super(key, value, additionalErrorMessage);
    }
}
