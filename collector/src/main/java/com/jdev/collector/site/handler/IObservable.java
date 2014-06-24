/**
 * 
 */
package com.jdev.collector.site.handler;

import com.jdev.collector.job.exception.EmergencyStopExecutionException;

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
    void notifyListeners() throws EmergencyStopExecutionException;
}
