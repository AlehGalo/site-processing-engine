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
     *
     */
    private static final String ERROR_FORMAT = "Error extracting regexp from HTML page: [name=%s] [value=%s]";

    /**
     *
     */
    private static final String DETAILED_ERROR_FORMAT = ERROR_FORMAT + "\tDetails: [%s]";

    /**
     * @param key
     * @param value
     * @param cause
     */
    public RegexpSelectionException(final String key, final String value, final Throwable cause) {
        super(String.format(ERROR_FORMAT, key, value), cause);
    }

    /**
     * @param key
     * @param value
     */
    public RegexpSelectionException(final String key, final String value) {
        super(String.format(ERROR_FORMAT, key, value));
    }

    /**
     * @param key
     * @param value
     * @param additionalErrorMessage
     */
    public RegexpSelectionException(final String key, final String value,
            final String additionalErrorMessage) {
        super(String.format(DETAILED_ERROR_FORMAT, key, value, additionalErrorMessage));
    }
}
