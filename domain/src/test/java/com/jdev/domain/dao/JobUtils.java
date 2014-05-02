/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;

import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
final class JobUtils {

    /**
     * 
     */
    private JobUtils() {
    }

    /**
     * @param reason
     * @return
     */
    public static Job createJob(final String reason) {
        Job job = new Job();
        job.setStartTime(new Date());
        job.setEndTime(new Date());
        job.setStatus("IN PROGRESS");
        // job.setDatabaseErrorsCount(0);
        // job.setCrawlerErrorsCount(0);
        job.setReasonOfStopping(reason);
        return job;
    }

}
