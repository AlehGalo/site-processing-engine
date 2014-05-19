/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.domain.Credential;
import com.jdev.domain.domain.Site;

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

    /**
     * Fake site.
     */
    private final Site site = new Site("Desc", "resourceurl", "url");

    @Override
    Credential createEntity() {
        siteDao.save(site);
        Credential credential = new Credential("username", "password");
        credential.setSite(site);
        return credential;
    }

    @Override
    Credential createUpdateEntity() {
        siteDao.save(site);
        Credential credential = new Credential("username1", "password1");
        credential.setSite(site);
        return credential;
    }
}
