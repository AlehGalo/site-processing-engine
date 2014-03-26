/**
 * 
 */
package com.jdev.crawler.core.process.model;

import java.nio.charset.Charset;

/**
 * @author Aleh
 * 
 */
public interface IEntity {

    /**
     * @return
     */
    String getMimeType();

    /**
     * @return
     */
    byte[] getContent();

    /**
     * @return
     */
    String getContentFileRef();

    /**
     * @return
     */
    Charset getCharset();

    /**
     * @return
     */
    int getStatusCode();
}
