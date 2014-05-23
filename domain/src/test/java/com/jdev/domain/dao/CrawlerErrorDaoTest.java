/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.domain.CrawlerError;
import com.jdev.domain.domain.Credential;
import com.jdev.domain.domain.Job;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
public class CrawlerErrorDaoTest extends AbstractWriteDaoTest<CrawlerError> {

    /**
     * 
     */
    @Autowired
    private IWriteDao<Job> jobDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<Credential> credentialDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<Site> siteDao;

    @Override
    CrawlerError createEntity() {
        return EntityUtils.createCrawlerErrorWithDependencies(jobDao, credentialDao, siteDao);
    }

    @Override
    CrawlerError createUpdateEntity() {
        return EntityUtils.createCrawlerErrorWithDependencies(jobDao, credentialDao, siteDao);
    }

}
