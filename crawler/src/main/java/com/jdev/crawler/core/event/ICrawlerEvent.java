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
    EventEnum getEvent();

    /**
     * @return
     */
    String getMessage();
}
