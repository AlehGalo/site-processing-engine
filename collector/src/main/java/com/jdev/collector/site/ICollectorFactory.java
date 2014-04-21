/**
 * 
 */
package com.jdev.collector.site;

import com.jdev.collector.site.handler.IObserver;

/**
 * @author Aleh
 * 
 */
public interface ICollectorFactory {

    /**
     * @param loginName
     * @param delegate
     * @return
     */
    ICollector getCollector(final String loginName, final IObserver delegate);

}
