/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Aleh
 * 
 */
@EnableScheduling
@Configuration
public class JobConfiguration {

    @Bean
    public FlRuJob flruJob() {
        return new FlRuJob();
    }

    @Bean
    public FreelanceComJob freelanceComJob() {
        return new FreelanceComJob();
    }

    @Bean
    public FreelancerComJob freelancerComJob() {
        return new FreelancerComJob();
    }

}
