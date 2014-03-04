/**
 *
 */
package com.jdev.crawler.exception;

import static java.lang.String.format;

/**
 * @author Aleh
 */
public class SelectionException extends CrawlerException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error format.
     */
    private static final String ERROR_FORMAT = "Error extracting selector from page: [name=%s] [value=%s]";

    /**
     * Error format.
     */
    private static final String DETAILED_ERROR_FORMAT = "\tDetails: [%s]";

    /**
     * @param message
     */
    public SelectionException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public SelectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param key
     *            of selector.
     * @param value
     *            of selector.
     */
    public SelectionException(final String key, final String value) {
        this(combineErrorMessage(ERROR_FORMAT, key, value));
    }

    /**
     * @param key
     *            of selector.
     * @param value
     *            of selector.
     */
    public SelectionException(final String key, final String value, final Throwable cause) {
        this(combineErrorMessage(ERROR_FORMAT, key, value), cause);
    }

    /**
     * @param key
     *            of selector.
     * @param value
     *            of selector.
     */
    public SelectionException(final String key, final String value, final String cause) {
        this(combineDetailedErrorMessage(ERROR_FORMAT, key, value, cause));
    }

    /**
     * @param key
     *            for key/value pair.
     * @param value
     *            of key/value pair.
     * @return Error message applied by format.
     */
    protected static String combineErrorMessage(final String format, final String key,
            final String value) {
        return format(format, key, value);
    }

    /**
     * @param key
     *            for key/value pair.
     * @param value
     *            of key/value pair.
     * @return Error message applied by format.
     */
    protected static String combineDetailedErrorMessage(final String format, final String key,
            final String value, final String details) {
        return format(format + DETAILED_ERROR_FORMAT, key, value, details);
    }
}
