/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;
import java.util.UUID;

import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.JobStatusEnum;
import com.jdev.domain.entity.PersistentError;
import com.jdev.domain.entity.Site;

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
        job.setStatus(JobStatusEnum.STARTED);
        return job;
    }

    /**
     * @return Credential entity.
     */
    public static Credential createCredential() {
        Credential credential = new Credential();
        credential.setPassword(generatedUUIDAsString());
        credential.setUsername(generatedUUIDAsString());
        return credential;
    }

    /**
     * @param jobDao
     * @return
     */
    public static DatabaseError createDatabaseError() {
        DatabaseError error = new DatabaseError();
        error.setUrl(generatedUUIDAsString());
        PersistentError errorPer = error.getError();
        errorPer.setError(generatedUUIDAsString());
        error.setError(errorPer);
        return error;
    }

    /**
     * @param jobDao
     * @param credeDao
     * @return
     */
    public static DatabaseError createDatabaseErrorWithDependencies(final IWriteDao<Job> jobDao,
            final IWriteDao<Credential> credeDao, final IWriteDao<Site> siteDao) {
        DatabaseError databaseError = createDatabaseError();
        PersistentError errorPer = databaseError.getError();
        errorPer.setJob(createPersistentJob(jobDao, credeDao, siteDao));
        errorPer.setError(generatedUUIDAsString());
        databaseError.setUrl(generatedUUIDAsString());
        databaseError.setError(errorPer);
        return databaseError;
    }

    /**
     * @param jobDao
     * @return
     */
    private static Job createPersistentJob(final IWriteDao<Job> jobDao,
            final IWriteDao<Credential> credeDao, final IWriteDao<Site> siteDao) {
        Job job = createJobWithDependencies(generatedUUIDAsString(), siteDao, credeDao);
        jobDao.save(job);
        return job;
    }

    /**
     * @return
     */
    public static CrawlerError createCrawlerErrorWithDependencies(final IWriteDao<Job> jobDao,
            final IWriteDao<Credential> credeDao, final IWriteDao<Site> siteDao) {
        CrawlerError error = new CrawlerError();
        PersistentError errorPer = error.getError();
        errorPer.setJob(createPersistentJob(jobDao, credeDao, siteDao));
        errorPer.setError(generatedUUIDAsString());
        error.setError(errorPer);
        return error;
    }

    /**
     * @return
     */
    public static Site createSite() {
        return new Site(generatedUUIDAsString(), generatedUUIDAsString(), generatedUUIDAsString());
    }

    /**
     * @param reason
     * @param siteDao
     * @param credentialDao
     * @return
     */
    public static Job createJobWithDependencies(final String reason, final IWriteDao<Site> siteDao,
            final IWriteDao<Credential> credentialDao) {
        Job job = createJob(reason);
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
        article.setOriginalArticleUrl(generatedUUIDAsString());
        article.setTitle(generatedUUIDAsString());
        article.setJob(createPersistentJob(jobDao, credentialDao, siteDao));
        return article;
    }

    /**
     * @param reason
     * @param siteDao
     * @param credentialDao
     * @return
     */
    public static Credential createCredentialWithDependencies(final IWriteDao<Site> siteDao) {
        Credential cred = createCredential();
        Site site = createSite();
        siteDao.save(site);
        cred.setSite(site);
        return cred;
    }

    /**
     * @return UUID as string.
     */
    private static String generatedUUIDAsString() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}
