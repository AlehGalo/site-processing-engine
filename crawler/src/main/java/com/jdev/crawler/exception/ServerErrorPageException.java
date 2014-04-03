/**
 * 
 */
package com.jdev.crawler.exception;

/**
 * @author Aleh
 * 
 */
public class ServerErrorPageException extends InvalidPageException {

    /**
     * Default seial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public ServerErrorPageException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ServerErrorPageException(final String message, final Throwable cause) {
        super(message, cause);
    }

}