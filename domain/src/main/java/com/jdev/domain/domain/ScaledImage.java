/**
 * 
 */
package com.jdev.domain.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "SCALED_IMAGE")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "SCALED_IMAGE_ID")) })
public class ScaledImage extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer length;

    /**
     * image/jpeg and so on.
     */
    @Column(name = "CONTENT_TYPE")
    private String contentType;

    /**
     * File system path of the resource image.
     */
    @Column(nullable = false, name = "SOURCE", columnDefinition = "MEDIUMBLOB")
    private byte[] source;

    /**
     * 
     */
    protected ScaledImage() {
    }

    /**
     * @param length
     * @param source
     * @param image
     */
    public ScaledImage(final Integer length, final byte[] source) {
        this.length = length;
        this.source = source;
    }

    /**
     * @return the length
     */
    public final Integer getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public final void setLength(final Integer length) {
        this.length = length;
    }

    /**
     * @return the source
     */
    public final byte[] getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public final void setSource(final byte[] source) {
        this.source = source;
    }

    /**
     * @return the contentType
     */
    public final String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public final void setContentType(final String contentType) {
        this.contentType = contentType;
    }
}
