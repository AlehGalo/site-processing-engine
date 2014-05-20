/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;

import com.jdev.domain.domain.Credential;
import com.jdev.domain.domain.Job;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
final class EntityUtils {

    /**
     * 
     */
    private EntityUtils() {
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
        job.setReasonOfStopping(reason);
        return job;
    }

    /**
     * @return Credential entity.
     */
    public static Credential createCredential() {
        Credential credential = new Credential();
        credential.setPassword(String.valueOf(System.currentTimeMillis()));
        credential.setUsername(String.valueOf(System.currentTimeMillis()));
        return credential;
    }

    /**
     * @return
     */
    public static Site createSite() {
        return new Site(String.valueOf(System.currentTimeMillis()), String.valueOf(System
                .currentTimeMillis()), String.valueOf(System.currentTimeMillis()));
    }

}
