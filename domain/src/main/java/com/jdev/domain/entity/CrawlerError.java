package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CRAWLER_ERROR")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "CRAWLER_ERROR_ID")) })
public class CrawlerError extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

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
