/**
 * 
 */
package com.jdev.domain.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    @Transient
    // @Formula("(select count(*) from ad_category_hierarchy ah join " +
    // "ad_categories ac on ac.id = ah.childid where ah.parentid = id)")
    private int crawlerErrorsCount;

    /**
     * 
     */
    @Transient
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
}