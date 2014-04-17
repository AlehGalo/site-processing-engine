/**
 * 
 */
package com.jdev.collector.site;


/**
 * @author Aleh
 * 
 */
public interface ICollectorFactory {

    /**
     * @param userData
     * @return
     */
    ICollector getCollector(String userData);

}
