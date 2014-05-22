package com.jdev.domain.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    @Column(name = "URL", nullable = false, columnDefinition = "VARCHAR(256)")
    private String url;

    /**
     * 
     */
    @Column(name = "ERROR", nullable = false, columnDefinition = "VARCHAR(1024)")
    private String error;

    /**
     * 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_JOB_ID")
    private Job job;

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

    /**
     * @return the error
     */
    public final String getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public final void setError(final String error) {
        this.error = error;
    }

    /**
     * @return the job
     */
    public final Job getJob() {
        return job;
    }

    /**
     * @param job
     *            the job to set
     */
    public final void setJob(final Job job) {
        this.job = job;
    }

}
