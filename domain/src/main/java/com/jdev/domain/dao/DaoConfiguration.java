/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Recommendation;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DaoConfiguration {

    /**
     * @return dao bean.
     */
    @Bean
    @Scope
    public IWriteDao<Site> siteDao() {
        return new AbstractCommonGenericWriteDao<Site>() {
            @Override
            public Class<Site> getPersistentClass() {
                return Site.class;
            }
        };
    }

    /**
     * @return
     */
    @Bean
    public IWriteDao<Article> articleDao() {
        return new AbstractCommonGenericWriteDao<Article>() {
            @Override
            public Class<Article> getPersistentClass() {
                return Article.class;
            }
        };
    }

    @Bean
    public IWriteDao<Recommendation> recommendationDao() {
        return new AbstractCommonGenericWriteDao<Recommendation>() {
            @Override
            public Class<Recommendation> getPersistentClass() {
                return Recommendation.class;
            }
        };
    }
}