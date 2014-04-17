/**
 * 
 */
package com.jdev.collector.site.handler;

/**
 * @author Aleh
 * 
 */
public interface IObservable {

    /**
     * @param listener
     */
    void addListener(IObserver listener);

    /**
     * @param listener
     */
    void removeListener(IObserver listener);

    /**
     * 
     */
    void notifyListeners();
}
