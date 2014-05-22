/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.CrawlerError;
import com.jdev.domain.domain.Credential;
import com.jdev.domain.domain.DatabaseError;
import com.jdev.domain.domain.Job;
import com.jdev.domain.domain.Recommendation;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh Dao beans configuration.
 */
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
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

}