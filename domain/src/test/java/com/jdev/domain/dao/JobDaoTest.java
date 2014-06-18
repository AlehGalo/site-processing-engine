/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.Site;

/**
 * @author Aleh
 * 
 */
public class JobDaoTest extends AbstractWriteDaoTest<Job> {

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
    Job createEntity() {
        return EntityUtils.createJobWithDependencies("ReasonNone", siteDao, credentialDao);
    }

    @Override
    Job createUpdateEntity() {
        return EntityUtils.createJobWithDependencies("ReasonNone1", siteDao, credentialDao);
    }
}
