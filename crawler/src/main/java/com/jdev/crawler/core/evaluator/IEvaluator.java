/**
 * 
 */
package com.jdev.crawler.core.evaluator;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public interface IEvaluator<T> {

    /**
     * @return object from evaluation.
     */
    T evaluate();
}
