/**
 * 
 */
package com.jdev.domain.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "JOB")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "JOB_ID")) })
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
    @Column(name = "STATUS", nullable = false, columnDefinition = "ENUM('FAILED_CRAWLER', 'FINISHED', 'STARTED', 'DB_ERRORS_MUCH') DEFAULT 'STARTED'")
    @Enumerated(EnumType.STRING)
    private JobStatusEnum status;

    /**
     * 
     */
    @Basic(fetch = FetchType.LAZY)
    @Formula("(SELECT COUNT(*) FROM CRAWLER_ERROR ce where ce.FK_JOB_ID = JOB_ID)")
    private Integer crawlerErrorsCount;

    /**
     * 
     */
    @Basic(fetch = FetchType.LAZY)
    @Formula("(SELECT COUNT(*) FROM DATABASE_ERROR de where de.FK_JOB_ID = JOB_ID)")
    private Integer databaseErrorsCount;

    /**
     * 
     */
    @Basic(fetch = FetchType.LAZY)
    @Formula("(SELECT COUNT(*) FROM ARTICLE a where a.FK_JOB_ID = JOB_ID)")
    private Integer recordsCount;

    /**
     * @param startTime
     * @param endTime
     * @param status
     * @param crawlerErrorsCount
     * @param databaseErrorsCount
     * @param recordsCount
     */
    public Job(final Date startTime, final Date endTime, final JobStatusEnum status,
            final Integer crawlerErrorsCount, final Integer databaseErrorsCount,
            final Integer recordsCount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.crawlerErrorsCount = crawlerErrorsCount;
        this.databaseErrorsCount = databaseErrorsCount;
        this.recordsCount = recordsCount;
    }

    /**
     * Constructor.
     */
    public Job() {
        setStatus(JobStatusEnum.STARTED);
    }

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

    /**
     * @return the status
     */
    public final JobStatusEnum getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public final void setStatus(final JobStatusEnum status) {
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
     * @return the recordsCount
     */
    public final Integer getRecordsCount() {
        return recordsCount;
    }

    /**
     * @param recordsCount
     *            the recordsCount to set
     */
    public final void setRecordsCount(final Integer recordsCount) {
        this.recordsCount = recordsCount;
    }
}