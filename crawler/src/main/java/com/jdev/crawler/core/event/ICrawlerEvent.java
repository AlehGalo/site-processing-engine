/**
 *
 */
package com.jdev.crawler.core.event;

/**
 * @author Aleh
 */
public interface ICrawlerEvent {

    /**
     * @return
     */
    Event getEvent();

    /**
     * @return
     */
    String getMessage();
}
