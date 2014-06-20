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
     * 
     */
    private int databaseErrorCount, crawlerErrorCount, articlesCollected;

    /**
     * @param startTime
     * @param endTime
     * @param status
     * @param siteUrl
     */
    public JobDto(final Date startTime, final Date endTime, final JobStatusEnum status,
            final String siteUrl) {
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
     * @return the siteUrl
     */
    public final String getSiteUrl() {
        return siteUrl;
    }

    /**
     * @param siteUrl
     *            the siteUrl to set
     */
    public final void setSiteUrl(final String siteUrl) {
        this.siteUrl = siteUrl;
    }

    /**
     * @return the databaseErrorCount
     */
    public final int getDatabaseErrorCount() {
        return databaseErrorCount;
    }

    /**
     * @param databaseErrorCount
     *            the databaseErrorCount to set
     */
    public final void setDatabaseErrorCount(final int databaseErrorCount) {
        this.databaseErrorCount = databaseErrorCount;
    }

    /**
     * @return the crawlerErrorCount
     */
    public final int getCrawlerErrorCount() {
        return crawlerErrorCount;
    }

    /**
     * @param crawlerErrorCount
     *            the crawlerErrorCount to set
     */
    public final void setCrawlerErrorCount(final int crawlerErrorCount) {
        this.crawlerErrorCount = crawlerErrorCount;
    }

    /**
     * @return the articlesCollected
     */
    public final int getArticlesCollected() {
        return articlesCollected;
    }

    /**
     * @param articlesCollected
     *            the articlesCollected to set
     */
    public final void setArticlesCollected(final int articlesCollected) {
        this.articlesCollected = articlesCollected;
    }
}
