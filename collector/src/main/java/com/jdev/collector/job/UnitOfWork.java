/**
 * 
 */
package com.jdev.collector.job;

import java.util.Date;

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
    public void saveArticle(final Article article) {
        writeArticleDao.save(article);
    }

    @Override
    public void saveJob(final Job job) {
        jobDao.save(job);
        System.out.println(job.getId());
    }

    @Override
    public void updateJob(final Job job) {
        jobDao.update(job);
    }

    @Override
    public void increaseJobError(Job job) {
        job.setErrorsCount(job.getErrorsCount() + 1);
        job.setEndTime(new Date());
        job.setStatus("ERROR");
        System.out.println(job.getErrorsCount());
        jobDao.save(job);
    }

}
