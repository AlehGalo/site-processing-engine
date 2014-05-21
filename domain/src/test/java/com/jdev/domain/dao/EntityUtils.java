/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;

import com.jdev.domain.domain.Article;
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
        credential.setPassword(currentTimeAsString());
        credential.setUsername(currentTimeAsString());
        return credential;
    }

    /**
     * @return
     */
    public static Site createSite() {
        return new Site(currentTimeAsString(), currentTimeAsString(), currentTimeAsString());
    }

    /**
     * @param reason
     * @param siteDao
     * @param credentialDao
     * @return
     */
    public static Job createJobWithDependencies(final String reason, final IWriteDao<Site> siteDao,
            final IWriteDao<Credential> credentialDao) {
        Job job = EntityUtils.createJob(reason);
        Credential cred = createCredentialWithDependencies(siteDao);
        credentialDao.save(cred);
        job.setCredential(cred);
        return job;
    }

    /**
     * @param reason
     * @param siteDao
     * @param credentialDao
     * @return
     */
    public static Article createArticleWithDependencies(final String content, final String reason,
            final IWriteDao<Site> siteDao, final IWriteDao<Credential> credentialDao,
            final IWriteDao<Job> jobDao) {
        Article article = new Article(content);
        article.setOriginalArticleUrl(currentTimeAsString());
        article.setTitle(currentTimeAsString());
        Job job = EntityUtils.createJobWithDependencies(reason, siteDao, credentialDao);
        jobDao.save(job);
        article.setJob(job);
        return article;
    }

    /**
     * @param reason
     * @param siteDao
     * @param credentialDao
     * @return
     */
    public static Credential createCredentialWithDependencies(final IWriteDao<Site> siteDao) {
        Credential cred = EntityUtils.createCredential();
        Site site = EntityUtils.createSite();
        siteDao.save(site);
        cred.setSite(site);
        return cred;
    }

    /**
     * @return string presentation of current time mills.
     */
    private static String currentTimeAsString() {
        return String.valueOf(System.currentTimeMillis());
    }

}
