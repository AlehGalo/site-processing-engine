/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Credential;
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

    /**
     * Job dao.
     */
    @Autowired
    private IWriteDao<Credential> credentialDao;

    @Override
    Article createEntity() {
        Job job = EntityUtils.createJob("Reason1");
        Site site = EntityUtils.createSite();
        siteDao.save(site);
        Credential cred = EntityUtils.createCredential();
        cred.setSite(site);
        credentialDao.save(cred);
        job.setCredential(cred);
        jobDao.save(job);
        Article article = new Article("Article content");
        article.setOriginalArticleUrl("Original article url");
        article.setOriginalArticleUrl("Original Url");
        article.setJob(job);
        article.setTitle("Title");
        return article;
    }

    @Override
    Article createUpdateEntity() {
        Job job = EntityUtils.createJob("Reason2");
        Site site = EntityUtils.createSite();
        siteDao.save(site);
        Credential cred = EntityUtils.createCredential();
        cred.setSite(site);
        credentialDao.save(cred);
        job.setCredential(cred);
        jobDao.save(job);
        Article article = new Article("Article content2");
        article.setOriginalArticleUrl("Original article url2");
        article.setOriginalArticleUrl("Original Url");
        article.setJob(job);
        article.setTitle("Title1");
        return article;
    }

}
