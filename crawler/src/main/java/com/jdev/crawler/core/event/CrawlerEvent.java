/**
 *
 */
package com.jdev.crawler.core.event;

import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 */
public final class CrawlerEvent implements ICrawlerEvent {

    /**
     *
     */
    private Event event;

    /**
     *
     */
    private String message;

    /**
     * @param event
     *            notified.
     * @param message
     *            for information or display.
     */
    public CrawlerEvent(final Event event, final String message) {
	setEvent(event);
	setMessage(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.event.ICrawlerEvent#getEvent()
     */
    @Override
    public Event getEvent() {
	return event;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.event.ICrawlerEvent#getMessage()
     */
    @Override
    public String getMessage() {
	return message;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(final Event event) {
	Assert.notNull(event);
	this.event = event;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(final String message) {
	Assert.hasLength(message);
	this.message = message;
    }

}
