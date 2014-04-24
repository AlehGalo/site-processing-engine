/**
 * 
 */
package com.jdev.domain.domain;

import java.util.Date;

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

}