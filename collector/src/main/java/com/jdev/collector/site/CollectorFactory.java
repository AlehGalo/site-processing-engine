/**
 * 
 */
package com.jdev.collector.site;

import java.util.HashMap;
import java.util.Map;

import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class CollectorFactory implements ICollectorFactory {

    /**
     * 
     */
    private final Map<String, UserData> storage;

    /**
     * 
     */
    public CollectorFactory() {
        storage = new HashMap<>();
        initStorage();
    }

    /**
     * 
     */
    private void initStorage() {
        UserData userData = new UserData("informer-fl-ru", "aFGgR5435");
        userData.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "flru";
            }

            @Override
            public Integer getCompanyId() {
                return 1;
            }
        });
        storage.put(userData.getLogin(), userData);

        userData = new UserData("informer-fl-com", "aFSsSR5435");
        userData.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "freelance_com";
            }

            @Override
            public Integer getCompanyId() {
                return 2;
            }
        });
        storage.put(userData.getLogin(), userData);

        userData = new UserData("informer-freelancer-com", "EMPTY");
        userData.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "freelancer_com";
            }

            @Override
            public Integer getCompanyId() {
                return 3;
            }
        });
        storage.put(userData.getLogin(), userData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.collector.site.ICollectorFactory#getCollector(com.jdev.crawler
     * .core.user.UserData)
     */
    @Override
    public ICollector getCollector(final String loginName) {
        Assert.hasLength(loginName);
        UserData data = storage.get(loginName);
        Assert.notNull(data);
        return getCollector(data.getCompany().getCompanyId(), data);
    }

    /**
     * @param companyId
     * @param userData
     * @return
     */
    private ICollector getCollector(final int companyId, final UserData userData) {
        switch (companyId) {
        case 1:
            return new FlRuCollector(userData);
        case 2:
            return new FreelanceComCollector(userData);
        case 3:
            return new FreelancerComCollector(userData);
        default:
            return new NullCollector();
        }
    }
}
