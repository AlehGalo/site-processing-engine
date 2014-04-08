/**
 * 
 */
package com.jdev.domain.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedNativeQuery;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "IMAGE", uniqueConstraints = { @UniqueConstraint(columnNames = "URL") })
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "IMAGE_ID")) })
@SqlResultSetMapping(name = "implicitImage", entities = @EntityResult(entityClass = Image.class))
@NamedNativeQuery(name = "findAllImages", query = "select * from image", resultSetMapping = "implicitImage")
public class Image extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Url of the image;
     */
    @Column(nullable = false, name = "URL")
    private String url;

    /**
     * File system path of the resource image.
     */
    @Column(nullable = false, name = "SOURCE", columnDefinition = "MEDIUMBLOB")
    private byte[] source;

    /**
     * image/jpeg and so on.
     */
    @Column(name = "CONTENT_TYPE")
    private String contentType;

    /**
     * Length.
     */
    @Column(name = "Length")
    private Integer length;

    /**
     * Persistence layer usage only.
     */
    protected Image() {
    }

    /**
     * @param url
     * @param source
     */
    public Image(final String url, final byte[] source) {
        super();
        this.url = url;
        this.source = source;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
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

}
