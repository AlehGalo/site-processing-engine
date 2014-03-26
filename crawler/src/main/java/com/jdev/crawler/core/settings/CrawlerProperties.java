/**
 * 
 */
package com.jdev.crawler.core.settings;

/**
 * @author Aleh
 * 
 */
public final class CrawlerProperties {

    /**
     * Repeat time for evey page.
     */
    private static final int repeatTime = 5;

    /**
     * Waiting before retry.
     */
    private static final int waitInterval = 10000;

    /**
     * By default all html files are save at a hard drive.
     */
    private static final boolean storeMarkup = true;

    /**
     * 
     */
    private CrawlerProperties() {
    }

    /**
     * @return the repeatTime
     */
    public static int getRepeatTime() {
        return repeatTime;
    }

    /**
     * @return the waitInterval
     */
    public static int getWaitInterval() {
        return waitInterval;
    }

    /**
     * @return the storemarkup
     */
    public static boolean isStoremarkup() {
        return storeMarkup;
    }

}
