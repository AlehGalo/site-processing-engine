package com.jdev.crawler.exception;

/**
 *
 */
public class ProcessException extends CrawlerException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public ProcessException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
