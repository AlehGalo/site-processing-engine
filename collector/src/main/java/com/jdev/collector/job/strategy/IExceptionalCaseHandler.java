/**
 * 
 */
package com.jdev.collector.job.strategy;

/**
 * @author Aleh
 * 
 */
public interface IExceptionalCaseHandler<T extends Throwable> {

    /**
     * @param t
     * @return
     */
    boolean handle(T t);
}
