/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Credential;

/**
 * @author Aleh informer.mail@yandex.ru AfeDFe332Fef33fdd name: InformerFL
 */
@EnableScheduling
@Configuration
public class JobConfiguration {

    /**
     * Credentials dao.
     */
    @Autowired
    private IWriteDao<Credential> credentialDao;

    @Bean
    public FlRuJob flruJob() {
        return new FlRuJob(credentialDao.find(1L));
    }

    // @Bean
    // public FreelanceComJob freelanceComJob() {
    // return new FreelanceComJob(credentialDao.find(3L));
    // }
    //
    // @Bean
    // public FreelancerComJob freelancerComJob() {
    // return new FreelancerComJob(credentialDao.find(2L));
    // }

}
