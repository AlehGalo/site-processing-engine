/**
 * 
 */
package com.jdev.crawler.core.process.handler;


/**
 * @author Aleh
 * 
 */
public enum MimeType {

	HTML("text/html");

	/**
	 * Mime type text.
	 */
	String val;

	/**
	 * @param value
	 */
	MimeType(final String value) {
		this.val = value;
	}

}
