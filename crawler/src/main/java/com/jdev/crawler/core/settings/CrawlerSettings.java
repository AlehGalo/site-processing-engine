/**
 * 
 */
package com.jdev.crawler.core.settings;

/**
 * @author Aleh
 * 
 */
public final class CrawlerSettings implements ISettings {

    /**
     * Repeat time for evey page.
     */
    private final int repeatTime = 5;

    /**
     * Waiting before retry.
     */
    private final int waitInterval = 10000;

    /**
     * By default all html files are save at a hard drive.
     */
    private final boolean storeMarkup = true;

    /**
     * @return the repeatTime
     */
    @Override
    public int getRepeatTime() {
        return repeatTime;
    }

    /**
     * @return the waitInterval
     */
    @Override
    public int getWaitInterval() {
        return waitInterval;
    }

    /**
     * @return the storemarkup
     */
    @Override
    public boolean isStoremarkup() {
        return storeMarkup;
    }

}
