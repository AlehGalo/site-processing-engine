/**
 * 
 */
package com.jdev.collector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jdev.collector.job.IUnitOfWork;
import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.entity.Credential;

/**
 * @author Aleh informer.mail@yandex.ru AdfdGG#r%$#@$55345
 */
@EnableScheduling
@Configuration
public class JobConfiguration {

    /**
     * 
     */
    @Autowired
    private IUnitOfWork unitOfWork;

    /**
     * 
     */
    @Autowired
    private IReadDao<Credential> credentialDao;

    /*
     * @Bean(autowire = Autowire.BY_NAME) public ScanResourceJob flruJob() {
     * return new ScanResourceJob(new CollectorBuilder(SiteEnum.FREELANCE_RU,
     * credentialDao), unitOfWork); }
     * 
     * @Bean(autowire = Autowire.BY_NAME) public ScanResourceJob
     * freelanceComJob() { return new ScanResourceJob(new
     * CollectorBuilder(SiteEnum.FREELANCE_COM, credentialDao), unitOfWork); }
     * 
     * @Bean(autowire = Autowire.BY_NAME) public ScanResourceJob
     * freelancerComJob() { return new ScanResourceJob(new
     * CollectorBuilder(SiteEnum.FREELANCER_COM, credentialDao), unitOfWork); }
     */
}
