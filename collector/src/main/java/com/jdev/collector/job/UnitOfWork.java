/**
 * 
 */
package com.jdev.collector.job;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.dao.IComposer;
import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.DatabaseError;
import com.jdev.domain.entity.Job;

/**
 * @author Aleh
 * 
 */
@Repository
@Transactional
public class UnitOfWork implements IUnitOfWork {

    /**
     * 
     */
    @Autowired
    private IWriteDao<Article> writeArticleDao;

    /**
     * 
     */
    @Autowired
    private IReadDao<Article> readArticleDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<Job> jobDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<CrawlerError> crawlerErrorDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<DatabaseError> databaseErrorDao;
    /**
     * 
     */
    @Autowired
    private IComposer<Article> articleComposer;

    @Override
    public void saveArticle(final Article article) {
        writeArticleDao.save(article);
    }

    @Override
    public void saveJob(final Job job) {
        jobDao.save(job);
    }

    @Override
    public void updateJob(final Job job) {
        jobDao.update(job);
    }

    @Override
    public void saveDatabaseError(final DatabaseError error) {
        databaseErrorDao.save(error);
    }

    @Override
    public void saveCrawlerError(final CrawlerError error) {
        crawlerErrorDao.save(error);
    }

    @Override
    public boolean isArticleAbsent(final Article article) {
        ICriteriaComposer<Article> articleCriteriaComposer = articleComposer.getCriteriaComposer();
        return CollectionUtils.isEmpty(readArticleDao.find(articleCriteriaComposer
                .createCriteriaQueryByStringProperty("originalArticleUrl",
                        article.getOriginalArticleUrl())));
    }
}
