/**
 *
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 */
public class LoginException extends CrawlerException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public LoginException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public LoginException(final String message, final Throwable cause) {
        super(message, cause);
    }
}