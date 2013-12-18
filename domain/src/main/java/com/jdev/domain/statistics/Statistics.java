/**
 * 
 */
package com.jdev.domain.statistics;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.SetUtils;

/**
 * @author Aleh
 * 
 */
public class Statistics implements IStatistics {

    /**
     * Count of total links.
     */
    private final AtomicInteger totalLinksCount;

    /**
     * 
     */
    private long startTime;

    /**
     * 
     */
    private final Set<String> invalidUrlSet;

    /**
     * 
     */
    private long endTime;

    /**
     * 
     */
    private long data;

    /**
     * 
     */
    public Statistics() {
        invalidUrlSet = new CopyOnWriteArraySet<>();
        totalLinksCount = new AtomicInteger();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.domain.IStatistics#getTimeSpent()
     */
    @Override
    public long getTimeSpent() {
        return endTime - startTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.domain.IStatistics#getLinksCount()
     */
    @Override
    public int getTotalLinksCount() {
        return totalLinksCount.intValue();
    }

    /**
     * +1 to total links.
     */
    public void incrementTotalLinksCount() {
        totalLinksCount.incrementAndGet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.domain.IStatistics#getValidLinksCount()
     */
    @Override
    public int getValidLinksCount() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.domain.IStatistics#getSameDomainLinksCount()
     */
    @Override
    public int getSameDomainLinksCount() {
        return 0;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    public void captureStart() {
        setStartTime(System.currentTimeMillis());
    }

    public void captureEnd() {
        setEndTime(System.currentTimeMillis());
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getInvalidUrlLinks() {
        return SetUtils.unmodifiableSet(invalidUrlSet);
    }

    /**
     * @param invalidUrl
     */
    public void addInvalidUrl(final String invalidUrl) {
        invalidUrlSet.add(invalidUrl);
    }

    /**
     * @param invalidUrls
     */
    public void addInvalidUrls(final Collection<String> invalidUrls) {
        invalidUrlSet.addAll(invalidUrls);
    }

    /**
     * @param dataIncrement
     */
    public void increaseData(final long dataIncrement) {
        data += dataIncrement;
    }

    @Override
    public long getDataTransfered() {
        return data;
    }
}
