/**
 * 
 */
package com.jdev.collector.site.factory;

import static com.jdev.collector.job.UserDataUtils.createUserData;

import org.springframework.util.Assert;

import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.FlRuCollector;
import com.jdev.collector.site.FreelanceComCollector;
import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.collector.site.SiteEnum;
import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.entity.Credential;

/**
 * @author Aleh
 * 
 */
public class CollectorBuilder implements ICollectorBuilder {

    /**
     * Credentials dao.
     */
    private final IReadDao<Credential> credentialDao;

    /**
     * 
     */
    private final SiteEnum site;

    /**
     * 
     */
    private Credential credential;

    /**
     * @param site
     * @param credentialDao
     */
    public CollectorBuilder(final SiteEnum site, final IReadDao<Credential> credentialDao) {
        Assert.notNull(site);
        Assert.notNull(credentialDao);
        this.site = site;
        this.credentialDao = credentialDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.collector.site.factory.ICollectorBuilder#createCollector(com
     * .jdev.collector.site.SiteEnum)
     */
    @Override
    public AbstractCollector createCollector() {
        credential = credentialDao.get(((long) site.ordinal() + 1));
        Assert.notNull(credential);
        switch (site) {
        case FREELANCE_COM:
            return new FreelanceComCollector(createUserData(credential));
        case FREELANCE_RU:
            return new FlRuCollector(createUserData(credential));
        case FREELANCER_COM:
            return new FreelancerComCollector(createUserData(credential));
        default:
            throw new UnsupportedOperationException(site.name());
        }
    }

    @Override
    public Credential getCredential() {
        Assert.notNull(credential);
        return credential;
    }
}
