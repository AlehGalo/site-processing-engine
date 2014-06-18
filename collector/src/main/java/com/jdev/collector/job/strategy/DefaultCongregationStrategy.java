/**
 * 
 */
package com.jdev.collector.job.strategy;

/**
 * @author Aleh
 * 
 */
public class DefaultCongregationStrategy implements ICongragationFlowStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.collector.job.strategy.ICongragationFlowStrategy#isCongregationContinued
     * ()
     */
    @Override
    public boolean isCongregationContinued() {
        return true;
    }
}
