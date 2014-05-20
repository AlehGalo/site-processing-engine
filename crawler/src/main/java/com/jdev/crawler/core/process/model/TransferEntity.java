/**
 * 
 */
package com.jdev.crawler.core.process.model;

import java.net.URI;
import java.nio.charset.Charset;

import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.process.handler.MimeTypeUtil;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class TransferEntity implements IEntity {

    /**
     * 
     */
    private MimeType mimeType;

    /**
     * 
     */
    private String contentFileRef;

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
    private URI entityUri;

    /**
     * 
     */
    public TransferEntity() {
    }

    /**
     * @param mimeType
     * @param contentFileRef
     * @param charset
     * @param content
     * @param statusCode
     */
    public TransferEntity(final MimeType mimeType, final String contentFileRef,
            final Charset charset, final byte[] content, final int statusCode) {
        this.mimeType = mimeType;
        this.contentFileRef = contentFileRef;
        this.charset = charset;
        this.content = content;
        this.statusCode = statusCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.model.IEntity#getMimeType()
     */
    @Override
    public MimeType getMimeType() {
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
    public void setCharset(final Charset charset) {
        this.charset = charset;
    }

    /**
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(final String mimeType) {
        MimeType type = MimeTypeUtil.findMime(mimeType);
        Assert.notNull(type);
        this.mimeType = type;
    }

    /**
     * @param type
     */
    public void setMimeType(final MimeType type) {
        this.mimeType = type;
    }

    /**
     * @param contentFileRef
     *            the contentFileRef to set
     */
    public void setContentFileRef(final String contentFileRef) {
        this.contentFileRef = contentFileRef;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(final byte[] content) {
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
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public URI getEntityUri() {
        return entityUri;
    }

    /**
     * @param entityUri
     *            the entityUri to set
     */
    public final void setEntityUri(final URI entityUri) {
        this.entityUri = entityUri;
    }
}