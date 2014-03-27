package com.jdev.crawler.core.settings;

/**
 * @author Aleh
 * 
 */
public interface ISettings {

    /**
     * @return the repeatTime
     */
    public int getRepeatTime();

    /**
     * @return the waitInterval
     */
    public int getWaitInterval();

    /**
     * @return the storemarkup
     */
    public boolean isStoremarkup();
}
