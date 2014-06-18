/**
 * 
 */
package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "SITE", uniqueConstraints = { @UniqueConstraint(columnNames = "URL") })
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "SITE_ID")) })
@SqlResultSetMapping(name = "implicitSite", entities = @EntityResult(entityClass = Site.class))
@NamedNativeQuery(name = "findAllSite", query = "select * from site", resultSetMapping = "implicitSite")
@Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY)
public class Site extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Resource URL.
     */
    @Column(name = "URL", nullable = false, unique = true, columnDefinition = "varchar(128)")
    private String url;

    /**
     * Resource for downloading source.
     */
    @Column(name = "RESOURCE_URL", nullable = false, unique = true, columnDefinition = "varchar(128)")
    private String resourceUrl;

    /**
     * Resource description.
     */
    @Column(name = "DESCRIPTION", nullable = true, columnDefinition = "varchar(128)")
    private String description;

    /**
     * Protected constructor for persistence usage.
     */
    protected Site() {
    }

    /**
     * Publicly used constructor.
     * 
     * @param url
     *            resource url.
     * @param description
     *            any description.
     */
    public Site(final String url, final String description) {
        this(url, url, description);
    }

    /**
     * Publicly used constructor.
     * 
     * @param domainUrl
     * @param resourceUrl
     * @param description
     */
    public Site(final String domainUrl, final String resourceUrl, final String description) {
        setUrl(domainUrl);
        setDescription(description);
        setResourceUrl(resourceUrl);
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the resourceUrl
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * @param resourceUrl
     *            the resourceUrl to set
     */
    public void setResourceUrl(final String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}