/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.jdev.collector.job.strategy.IncreaseErrorCounterExceptionalHandler;
import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
@Service
@EnableScheduling
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
        setExceptionHandler(new IncreaseErrorCounterExceptionalHandler<Exception>() {
            @Override
            public void increaseErrors() {
                unitOfWork.increaseJobError(job);
            }
        });
    }
}
