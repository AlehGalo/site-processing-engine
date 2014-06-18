/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.Site;

/**
 * @author Aleh
 * 
 */
public class CredentialDaoTest extends AbstractWriteDaoTest<Credential> {

    /**
     * 
     */
    @Autowired
    private IWriteDao<Site> siteDao;

    @Override
    Credential createEntity() {
        return EntityUtils.createCredentialWithDependencies(siteDao);
    }

    @Override
    Credential createUpdateEntity() {
        return EntityUtils.createCredentialWithDependencies(siteDao);
    }
}
