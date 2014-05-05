/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Job;
import com.jdev.domain.domain.Recommendation;
import com.jdev.domain.domain.Site;

/**
 * @author Aleh
 * 
 */
public class RecommendationDaoTest extends AbstractWriteDaoTest<Recommendation> {

    /**
     * Write dao.
     */
    @Autowired
    private IWriteDao<Article> articleWriteDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<Site> siteDao;

    /**
     * 
     */
    @Autowired
    private IWriteDao<Job> jobDao;

    @Override
    Recommendation createEntity() {
        // Site site = new Site("http://site.com", "description");
        // siteDao.save(site);
        Job job = JobUtils.createJob("Reason1");
        jobDao.save(job);
        Recommendation recommendation = new Recommendation();
        Article article = new Article("ArticleContent");
        article.setOriginalArticleUrl("URL");
        article.setJob(job);
        // article.setSite(site);
        article.setTitle("TitleF");
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }

    @Override
    Recommendation createUpdateEntity() {
        // Site site = new Site("http://sites.com", "descriptions");
        // siteDao.save(site);
        Job job = JobUtils.createJob("Reason2");
        jobDao.save(job);
        Recommendation recommendation = new Recommendation();
        Article article = new Article("ArticleContentABC");
        article.setOriginalArticleUrl("URLABC");
        // article.setSite(site);
        article.setTitle("TitleABC");
        article.setJob(job);
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }

}
