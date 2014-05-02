/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;

import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
public class FreelancerComJob extends AbstractScanResourceJob {

    /**
     * 
     */
    private static final UserData userData = new UserData("informer-freelancer-com", "EMPTY");

    static {
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
    }

    /**
     * 
     */
    public FreelancerComJob() {
        super(new FreelancerComCollector(userData));
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void doTheJob() {
        scan();
    }

}
