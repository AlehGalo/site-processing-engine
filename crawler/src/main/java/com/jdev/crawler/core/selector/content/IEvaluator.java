/**
 * 
 */
package com.jdev.crawler.core.selector.content;

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
