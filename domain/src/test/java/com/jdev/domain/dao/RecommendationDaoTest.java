/**
 * 
 */
package com.jdev.domain.dao;

import static com.jdev.domain.InetAddressUtil.createIpv4Address;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.InetAddress;
import com.jdev.domain.domain.Recommendation;
import com.jdev.domain.domain.Site;
import com.jdev.domain.domain.Title;

/**
 * @author Aleh
 * 
 */
public class RecommendationDaoTest extends AbstractWriteDaoTest<Recommendation> {

    /**
     * Write dao.
     */
    @Autowired
    private IWriteDao<InetAddress> addressWriteDao;

    /**
     * Write dao.
     */
    @Autowired
    private IWriteDao<Article> articleWriteDao;

    /**
     * Write dao.
     */
    @Autowired
    private IReadDao<Site> siteReadDao;

    @Override
    Recommendation createEntity() {
        Recommendation recommendation = new Recommendation();
        InetAddress address = createIpv4Address("128.52.26.5");
        addressWriteDao.save(address);
        recommendation.setIpAddress(address);
        Article article = new Article("ArticleContent");
        article.setOriginalArticleUrl("URL");
        article.setSite(siteReadDao.find(1L));
        article.setTitle(new Title("TitleF"));
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }

    @Override
    Recommendation createUpdateEntity() {
        Recommendation recommendation = new Recommendation();
        InetAddress address = createIpv4Address("123.123.123.123");
        addressWriteDao.save(address);
        recommendation.setIpAddress(address);
        Article article = new Article("ArticleContentABC");
        article.setOriginalArticleUrl("URLABC");
        article.setSite(siteReadDao.find(1L));
        article.setTitle(new Title("TitleABC"));
        recommendation.setVote(false);
        articleWriteDao.save(article);
        recommendation.setArticle(article);
        return recommendation;
    }

}
