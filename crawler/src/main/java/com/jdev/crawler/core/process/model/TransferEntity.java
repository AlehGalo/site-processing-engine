/**
 * 
 */
package com.jdev.crawler.core.process.model;

import java.nio.charset.Charset;

/**
 * @author Aleh
 * 
 */
public class TransferEntity implements IEntity {

    /**
     * 
     */
    private String mimeType, contentFileRef;

    /**
     * 
     */
    private Charset charset;

    /**
     * 
     */
    private byte[] content;

    /**
     * 
     */
    private int statusCode;

    /**
     * 
     */
    public TransferEntity() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.model.IEntity#getMimeType()
     */
    @Override
    public String getMimeType() {
        return mimeType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.model.IEntity#getContent()
     */
    @Override
    public byte[] getContent() {
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.model.IEntity#getContentFileRef()
     */
    @Override
    public String getContentFileRef() {
        return contentFileRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.model.IEntity#getEncoding()
     */
    @Override
    public Charset getCharset() {
        return charset;
    }

    /**
     * @param charset
     *            the charset to set
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @param contentFileRef
     *            the contentFileRef to set
     */
    public void setContentFileRef(String contentFileRef) {
        this.contentFileRef = contentFileRef;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}