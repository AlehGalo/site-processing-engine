/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class InvalidPageException extends CrawlerException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public InvalidPageException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidPageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}