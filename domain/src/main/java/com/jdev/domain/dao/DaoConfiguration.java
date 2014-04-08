/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Image;
import com.jdev.domain.domain.InetAddress;
import com.jdev.domain.domain.Preview;
import com.jdev.domain.domain.Recommendation;
import com.jdev.domain.domain.ScaledImage;
import com.jdev.domain.domain.Site;
import com.jdev.domain.domain.Title;

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
    public IReadDao<Site> siteDao() {
        return new AbstractCommonGenericReadDao<Site>() {
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
    public IWriteDao<Preview> previewDao() {
        return new AbstractCommonGenericWriteDao<Preview>() {
            @Override
            public Class<Preview> getPersistentClass() {
                return Preview.class;
            }
        };
    }

    /**
     * @return
     */
    @Bean
    public IWriteDao<Title> titleDao() {
        return new AbstractCommonGenericWriteDao<Title>() {
            @Override
            public Class<Title> getPersistentClass() {
                return Title.class;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.jdev.domain.dao.AbstractCommonGenericReadDao#findAll(int,
             * int, java.lang.String, java.util.Date, java.util.Date)
             */
            @Override
            public List<Title> findAll(final int startPosition, final int resultNumber,
                    final String sortBy, final Date start, final Date end) {
                final ICriteriaComposer<Title> composer = getCriteriaComposer();
                final CriteriaQuery<Title> criteriaQuery = composer.createCriteriaQuery();
                final Root<Title> root = criteriaQuery.from(getPersistentClass());
                final Path<Date> path = root.get(sortBy);
                Predicate predicate;
                final Order order = composer.getCriteriaBuilder().desc(path);
                TypedQuery<Title> typedQuery;
                if (start != null && end != null) {
                    predicate = composer.getCriteriaBuilder().between(path, start, end);
                    typedQuery = composer.createDecoratedQueryStartEnd(
                            criteriaQuery.where(predicate).orderBy(order), startPosition,
                            resultNumber);
                } else {
                    typedQuery = composer.createDecoratedQueryStartEnd(
                            criteriaQuery.orderBy(order), startPosition, resultNumber);
                }
                return typedQuery.getResultList();
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.jdev.domain.dao.AbstractCommonGenericReadDao#count(javax.
             * persistence.criteria.CriteriaQuery)
             */
            @Override
            public long count(final Date start, final Date end) {
                final ICriteriaComposer<Title> composer = getCriteriaComposer();
                final CriteriaQuery<Long> query = composer.createCriteriaQuery(Long.class);
                final Root<Title> root = query.from(getPersistentClass());
                final Predicate predicate = composer.getCriteriaBuilder().between(
                        root.<Date> get("dateTime"), start, end);
                query.select(composer.getCriteriaBuilder().count(root));
                query.where(predicate);
                return getEntityManager().createQuery(query).getSingleResult();
            }
        };
    }

    /**
     * @return
     */
    @Bean
    public IWriteDao<Image> imageDao() {
        return new AbstractCommonGenericWriteDao<Image>() {
            @Override
            public Class<Image> getPersistentClass() {
                return Image.class;
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

    /**
     * @return
     */
    @Bean
    public IWriteDao<ScaledImage> scaledImageDao() {
        return new AbstractCommonGenericWriteDao<ScaledImage>() {
            @Override
            public Class<ScaledImage> getPersistentClass() {
                return ScaledImage.class;
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

    @Bean
    public IWriteDao<InetAddress> inetAddressDao() {
        return new AbstractCommonGenericWriteDao<InetAddress>() {
            @Override
            public Class<InetAddress> getPersistentClass() {
                return InetAddress.class;
            }
        };
    }

}