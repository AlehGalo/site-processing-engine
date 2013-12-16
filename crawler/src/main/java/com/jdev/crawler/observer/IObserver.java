/**
 *
 */
package com.jdev.crawler.observer;

/**
 * @author Aleh
 * @param <T>
 */
public interface IObserver<T> {

    /**
     * @param event
     */
    void onAction(T event);
}
