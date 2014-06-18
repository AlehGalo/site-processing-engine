/**
 * 
 */
package com.jdev.collector.job.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ReflectionUtils;

/**
 * @author Aleh
 * 
 */
@Component("errorHandler")
public class StopAtFirstErrorErrorHandler implements ErrorHandler {

    /**
     * Logging.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(StopAtFirstErrorErrorHandler.class);

    @Override
    public void handleError(final Throwable t) {
        LOGGER.error(t.getMessage(), t);
        ReflectionUtils.rethrowRuntimeException(t);
    }

}
