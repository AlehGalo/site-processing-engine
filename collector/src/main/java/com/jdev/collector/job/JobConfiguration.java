/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jdev.collector.site.FlRuCollector;
import com.jdev.collector.site.FreelanceComCollector;
import com.jdev.collector.site.FreelancerComCollector;
import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.entity.Credential;

/**
 * @author Aleh informer.mail@yandex.ru AdfdGG#r%$#@$55345
 */
@EnableScheduling
@Configuration
public class JobConfiguration {

    /**
     * Credentials dao.
     */
    @Autowired
    private IReadDao<Credential> credentialDao;

    /**
     * Unit of transactional work for job.
     */
    @Autowired
    private IUnitOfWork unitOfWork;

    @Bean
    public ScanResourceJob flruJob() {
        Credential credential = credentialDao.get(1L);
        return new ScanResourceJob(new FlRuCollector(UserDataUtils.createUserData(credential)),
                credential, unitOfWork);
    }

    @Bean
    public ScanResourceJob freelanceComJob() {
        Credential credential = credentialDao.get(3L);
        return new ScanResourceJob(new FreelanceComCollector(
                UserDataUtils.createUserData(credential)), credential, unitOfWork);
    }

    @Bean
    public ScanResourceJob freelancerComJob() {
        Credential credential = credentialDao.get(2L);
        return new ScanResourceJob(new FreelancerComCollector(
                UserDataUtils.createUserData(credential)), credential, unitOfWork);
    }
}
