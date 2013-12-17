/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class CrawlerException extends Exception {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public CrawlerException(final String message) {
        this(message, null);
    }

    /**
     * @param message
     * @param cause
     */
    public CrawlerException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}