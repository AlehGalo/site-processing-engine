/**
 * 
 */
package com.jdev.collector.job.strategy;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ReflectionUtils;

/**
 * @author Aleh
 * 
 */
@Component("errorHandler")
public class StopAtFirstErrorErrorHandler implements ErrorHandler {

    @Override
    public void handleError(final Throwable t) {
        ReflectionUtils.rethrowRuntimeException(t);
    }

}
