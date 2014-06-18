/**
 * 
 */
package com.jdev.domain.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "JOB")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "JOB_ID")) })
public class Job extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column(name = "START_TIME", nullable = false, columnDefinition = "DATETIME")
    private Date startTime;

    /**
     * 
     */
    @Column(name = "END_TIME", nullable = false, columnDefinition = "DATETIME")
    private Date endTime;

    /**
     * 
     */
    @Column(name = "STOP_REASON", nullable = false, columnDefinition = "VARCHAR(256)")
    private String reasonOfStopping;

    /**
     * 
     */
    @Column(name = "STATUS", nullable = false, columnDefinition = "VARCHAR(16)")
    private String status;

    /**
     * 
     */
    @Column(name = "CRAWLER_ERRORS_COUNT", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int crawlerErrorsCount;

    /**
     * 
     */
    @Column(name = "DATABASE_ERRORS_COUNT", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int databaseErrorsCount;

    /**
     * 
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CREDENTIAL_ID")
    private Credential credential;

    /**
     * @return the startTime
     */
    public final Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public final void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public final Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public final void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the reasonOfStopping
     */
    public final String getReasonOfStopping() {
        return reasonOfStopping;
    }

    /**
     * @param reasonOfStopping
     *            the reasonOfStopping to set
     */
    public final void setReasonOfStopping(final String reasonOfStopping) {
        this.reasonOfStopping = reasonOfStopping;
    }

    /**
     * @return the status
     */
    public final String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public final void setStatus(final String status) {
        this.status = status;
    }

    /**
     * @return the crawlerErrorsCount
     */
    public final Integer getCrawlerErrorsCount() {
        return crawlerErrorsCount;
    }

    /**
     * @param crawlerErrorsCount
     *            the crawlerErrorsCount to set
     */
    public final void setCrawlerErrorsCount(final Integer crawlerErrorsCount) {
        this.crawlerErrorsCount = crawlerErrorsCount;
    }

    /**
     * @return the databaseErrorsCount
     */
    public final Integer getDatabaseErrorsCount() {
        return databaseErrorsCount;
    }

    /**
     * @param databaseErrorsCount
     *            the databaseErrorsCount to set
     */
    public final void setDatabaseErrorsCount(final Integer databaseErrorsCount) {
        this.databaseErrorsCount = databaseErrorsCount;
    }

    /**
     * @return the credential
     */
    public final Credential getCredential() {
        return credential;
    }

    /**
     * @param credential
     *            the credential to set
     */
    public final void setCredential(final Credential credential) {
        this.credential = credential;
    }
}