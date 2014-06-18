/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.entity.Site;

/**
 * @author Aleh
 * 
 */
public class SiteDaoTest extends AbstractWriteDaoTest<Site> {

    @Override
    Site createEntity() {
        return EntityUtils.createSite();
    }

    @Override
    Site createUpdateEntity() {
        return EntityUtils.createSite();
    }

}
