package com.jdev.crawler.observer;

/**
 * @author Aleh
 * @param <T>
 */
public interface IObserverable<T> {

    /**
     * @param listener
     * @return
     */
    boolean addListener(IObserver<T> listener);

    /**
     * @param listener
     * @return
     */
    boolean deleteListener(IObserver<T> listener);

    /**
     * @param event
     */
    void notifyListeners(T event);
}
