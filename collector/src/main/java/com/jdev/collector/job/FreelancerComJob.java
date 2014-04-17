/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Aleh
 * 
 */
@Component
public class FreelancerComJob implements IScanResourceJob {

    /**
     * 
     */
    public FreelancerComJob() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.job.IScanResourceJob#scan()
     */
    @Override
    @Scheduled(fixedDelay = 3600000)
    public void scan() {
        // TODO Auto-generated method stub

    }

}
