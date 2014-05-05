/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
public class SiteDaoTest extends AbstractWriteDaoTest<Site> {

    @Override
    Site createEntity() {
        return new Site("domainUrl", "resourceUrl", "description");
    }

    @Override
    Site createUpdateEntity() {
        return new Site("domainUrl1", "resourceUrl1", "description1");
    }

}
