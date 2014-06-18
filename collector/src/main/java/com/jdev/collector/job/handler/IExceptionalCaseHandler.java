/**
 * 
 */
package com.jdev.collector.job.handler;

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
