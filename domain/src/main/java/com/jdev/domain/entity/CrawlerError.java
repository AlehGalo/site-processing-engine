package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CRAWLER_ERROR")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "CRAWLER_ERROR_ID")) })
@Embeddable
public class CrawlerError extends AbstractIdentifiable {

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
     * Constructor.
     */
    public CrawlerError() {
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

}
