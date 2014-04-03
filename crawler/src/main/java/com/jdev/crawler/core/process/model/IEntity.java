/**
 * 
 */
package com.jdev.crawler.core.process.model;

import java.nio.charset.Charset;

import com.jdev.crawler.core.process.handler.MimeType;

/**
 * @author Aleh
 * 
 */
public interface IEntity {

    /**
     * @return
     */
    MimeType getMimeType();

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
