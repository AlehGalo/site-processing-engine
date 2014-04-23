/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.jdev.collector.site.FlRuCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
@Service
@EnableScheduling
public class FlRuJob extends AbstractScanResourceJob {

    /**
     * 
     */
    private static UserData userData = new UserData("informer-fl-ru", "aFGgR5435");

    static {
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

    }

    /**
     * 
     */
    public FlRuJob() {
        super(new FlRuCollector(userData));
    }
}