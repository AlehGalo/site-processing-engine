/**
 * 
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 * 
 */
@Deprecated
public class PhoneNumberException extends CrawlerException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public PhoneNumberException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public PhoneNumberException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
