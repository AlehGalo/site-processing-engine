/**
 * 
 */
package com.jdev.collector.job.strategy;

/**
 * @author Aleh
 * 
 */
public class LimitedCongregationStrategy implements ICongragationFlowStrategy {

    /**
     * Limit of steps.
     */
    private final int limit;

    /**
     * Local call counter.
     */
    private int counter;

    /**
     * @param limit
     *            limitation.
     */
    public LimitedCongregationStrategy(final int limit) {
        this.limit = limit;
        counter = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.collector.job.strategy.ICongragationFlowStrategy#isCongregationContinued
     * ()
     */
    @Override
    public boolean isCongregationContinued() {
        return ++counter < limit;
    }

}
