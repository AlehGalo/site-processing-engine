/**
 * 
 */
package com.jdev.collector.job.strategy;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public class StopAtFirstErrorExceptionalHandler<T extends Throwable> implements
        IExceptionalCaseHandler<T> {

    @Override
    public boolean handle(final T t) {
        return false;
    }

}
