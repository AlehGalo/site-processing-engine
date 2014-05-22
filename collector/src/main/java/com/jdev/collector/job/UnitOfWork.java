/**
 * 
 */
package com.jdev.collector.job;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Job;

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
    private IWriteDao<Job> jobDao;

    @Override
    public boolean saveArticle(final Article article) {
        if (CollectionUtils.isEmpty(writeArticleDao.findByStringProperty("originalArticleUrl",
                article.getOriginalArticleUrl()))) {
            writeArticleDao.save(article);
            return true;
        }
        return false;
    }

    @Override
    public void saveJob(final Job job) {
        jobDao.save(job);
    }

    @Override
    public void updateJob(final Job job) {
        jobDao.update(job);
    }
}
