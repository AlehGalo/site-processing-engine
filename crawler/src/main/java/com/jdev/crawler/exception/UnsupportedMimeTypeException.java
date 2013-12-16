/**
 * 
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 * 
 */
public class UnsupportedMimeTypeException extends InvalidPageException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public UnsupportedMimeTypeException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public UnsupportedMimeTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
