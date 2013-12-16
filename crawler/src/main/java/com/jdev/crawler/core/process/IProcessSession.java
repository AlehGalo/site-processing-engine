/**
 * 
 */
package com.jdev.crawler.core.process;


/**
 * @author Aleh Session.
 */
public interface IProcessSession {

    /**
     * @return Process context.
     */
    IProcessContext getSessionContext();

    /**
     * @param name
     * @param value
     */
    Object putValue(String word, Object value);

    /**
     * @param name
     */
    String getStringValue(String word);

    /*
     * @param name
     */
    Object getValue(String word);
}
