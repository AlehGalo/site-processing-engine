/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;

import com.jdev.collector.site.FreelanceComCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
public class FreelanceComJob extends AbstractScanResourceJob {

    /**
     * 
     */
    private static UserData userData = new UserData("informer-fl-com", "aFSsSR5435");

    static {
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
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void doTheJob() {
        scan();
    }

    /**
     * 
     */
    public FreelanceComJob() {
        super(new FreelanceComCollector(userData));
    }
}