/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.Credential;
import com.jdev.domain.entity.Job;
import com.jdev.domain.entity.Recommendation;
import com.jdev.domain.entity.Site;

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

    /**
     * 
     */
    @Autowired
    private IWriteDao<Credential> credentialDao;

    @Override
    Recommendation createEntity() {
        Job job = EntityUtils.createJobWithDependencies("Reason1", siteDao, credentialDao);
        jobDao.save(job);
        Recommendation recommendation = new Recommendation();
        Article article = new Article("ArticleContent");
        article.setOriginalArticleUrl("URL");
        article.setJob(job);
        article.setTitle("TitleF");
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }

    @Override
    Recommendation createUpdateEntity() {
        Job job = EntityUtils.createJobWithDependencies("Reason2", siteDao, credentialDao);
        jobDao.save(job);
        Recommendation recommendation = new Recommendation();
        Article article = new Article("ArticleContentABC");
        article.setOriginalArticleUrl("URLABC");
        article.setTitle("TitleABC");
        article.setJob(job);
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }
}
