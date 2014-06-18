/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.Site;

/**
 * @author Aleh
 * 
 */
public class DatabaseErrorDaoTest extends AbstractWriteDaoTest<DatabaseError> {

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
    DatabaseError createEntity() {
        return EntityUtils.createDatabaseErrorWithDependencies(jobDao, credentialDao, siteDao);
    }

    @Override
    DatabaseError createUpdateEntity() {
        return EntityUtils.createDatabaseErrorWithDependencies(jobDao, credentialDao, siteDao);
    }

}
