/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
@Service(value = "freelancerCom")
public class FreelancerComJob extends AbstractScanResourceJob implements Tasklet {

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

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
            throws Exception {
        scan();
        return RepeatStatus.FINISHED;
    }
}
