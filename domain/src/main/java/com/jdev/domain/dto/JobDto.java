/**
 * 
 */
package com.jdev.domain.dto;

import java.io.Serializable;
import java.util.Date;

import com.jdev.domain.entity.JobStatusEnum;

/**
 * @author Aleh
 * 
 */
public class JobDto implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private JobStatusEnum status;

    /**
     * 
     */
    private String siteUrl;

    /**
     * @param startTime
     * @param endTime
     * @param status
     * @param siteUrl
     */
    public JobDto(Date startTime, Date endTime, JobStatusEnum status, String siteUrl) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.siteUrl = siteUrl;
    }

    /**
     * 
     */
    public JobDto() {
    }

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
    public final void setStartTime(Date startTime) {
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
    public final void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    public final void setStatus(JobStatusEnum status) {
        this.status = status;
    }

    /**
     * @return the siteUrl
     */
    public final String getSiteUrl() {
        return siteUrl;
    }

    /**
     * @param siteUrl
     *            the siteUrl to set
     */
    public final void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
