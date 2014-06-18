/**
 * 
 */
package com.jdev.domain.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jdev.domain.dao.CommonGenericReadDao;
import com.jdev.domain.dao.CommonGenericWriteDao;
import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.Recommendation;
import com.jdev.domain.entity.Site;

/**
 * @author Aleh Dao beans configuration.
 */
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@EnableJpaRepositories("com.jdev.domain.dao.repository")
@EnableJpaAuditing
// @EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DaoConfiguration {

    @Bean
    public IWriteDao<Site> siteDao() {
        return new CommonGenericWriteDao<Site>(Site.class);
    }

    @Bean
    public IWriteDao<Article> articleDao() {
        return new CommonGenericWriteDao<Article>(Article.class);
    }

    @Bean
    public IWriteDao<Recommendation> recommendationDao() {
        return new CommonGenericWriteDao<Recommendation>(Recommendation.class);
    }

    @Bean
    public IWriteDao<Job> jobDao() {
        return new CommonGenericWriteDao<Job>(Job.class);
    }

    @Bean
    public IWriteDao<Credential> credentialDao() {
        return new CommonGenericWriteDao<Credential>(Credential.class);
    }

    @Bean
    public IWriteDao<CrawlerError> crawlerErrorDao() {
        return new CommonGenericWriteDao<CrawlerError>(CrawlerError.class);
    }

    @Bean
    public IWriteDao<DatabaseError> databaseErrorDao() {
        return new CommonGenericWriteDao<DatabaseError>(DatabaseError.class);
    }

    @Bean
    public IReadDao<Site> siteReadDao() {
        return new CommonGenericReadDao<Site>(Site.class);
    }

    @Bean
    public IReadDao<Article> articleReadDao() {
        return new CommonGenericReadDao<Article>(Article.class);
    }

    @Bean
    public IReadDao<Recommendation> recommendationReadDao() {
        return new CommonGenericReadDao<Recommendation>(Recommendation.class);
    }

    @Bean
    public IReadDao<Job> jobReadDao() {
        return new CommonGenericReadDao<Job>(Job.class);
    }

    @Bean
    public IReadDao<Credential> credentialReadDao() {
        return new CommonGenericReadDao<Credential>(Credential.class);
    }

    @Bean
    public IReadDao<CrawlerError> crawlerReadErrorDao() {
        return new CommonGenericReadDao<CrawlerError>(CrawlerError.class);
    }

    @Bean
    public IReadDao<DatabaseError> databaseReadErrorDao() {
        return new CommonGenericReadDao<DatabaseError>(DatabaseError.class);
    }

}