/**
 * 
 */
package com.sswf.desti.domain.statistics;

import java.util.Set;

/**
 * @author Aleh
 * 
 */
public interface IStatistics {

    /**
     * @return
     */
    long getTimeSpent();

    /**
     * @return
     */
    int getTotalLinksCount();

    /**
     * @return
     */
    int getValidLinksCount();

    /**
     * @return
     */
    int getSameDomainLinksCount();

    /**
     * @return
     */
    Set<String> getInvalidUrlLinks();

    /**
     * @return
     */
    long getDataTransfered();
}
