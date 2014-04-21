/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.jdev.collector.site.FreelanceComCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
@Service
@EnableAsync
@EnableScheduling
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

    /**
     * 
     */
    public FreelanceComJob() {
        super(new FreelanceComCollector(userData));
    }
}