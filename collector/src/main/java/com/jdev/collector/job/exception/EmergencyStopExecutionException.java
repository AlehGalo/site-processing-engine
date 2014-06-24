/**
 * 
 */
package com.jdev.collector.job.exception;

import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class EmergencyStopExecutionException extends CrawlerException {

    /**
     * Default version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public EmergencyStopExecutionException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public EmergencyStopExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
