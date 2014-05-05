/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Job;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
public class ArticleDaoTest extends AbstractWriteDaoTest<Article> {

    /**
     * 
     */
    @Autowired
    private IWriteDao<Site> siteDao;

    /**
     * Job dao.
     */
    @Autowired
    private IWriteDao<Job> jobDao;

    @Override
    Article createEntity() {
        // Site site = new Site("http://site.com", "description");
        Job job = JobUtils.createJob("Reason1");
        jobDao.save(job);
        // siteDao.save(site);
        Article article = new Article("Article content");
        article.setOriginalArticleUrl("Original article url");
        article.setJob(job);
        // article.setSite(site);
        article.setTitle("Title");
        return article;
    }

    @Override
    Article createUpdateEntity() {
        // Site site = new Site("http://sites.com", "descriptions");
        // siteDao.save(site);
        Job job = JobUtils.createJob("Reason2");
        jobDao.save(job);
        Article article = new Article("Article content2");
        article.setOriginalArticleUrl("Original article url2");
        // article.setSite(site);
        article.setJob(job);
        article.setTitle("Title1");
        return article;
    }

}
