/**
 * 
 */
package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "DATABASE_ERROR")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "DATABASE_ERROR_ID")) })
public class DatabaseError extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Embedded
    private PersistentError error;

    /**
     * Url of the error resource.
     */
    @Column(name = "URL", nullable = false, columnDefinition = "VARCHAR(256)")
    private String url;

    /**
     * 
     */
    public DatabaseError() {
        setError(new PersistentError());
    }

    /**
     * @return the error
     */
    public final PersistentError getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public final void setError(final PersistentError error) {
        this.error = error;
    }

    /**
     * @return the url
     */
    public final String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public final void setUrl(final String url) {
        this.url = url;
    }
}