/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Site;
import com.jdev.domain.domain.Title;

/**
 * @author Aleh
 * 
 */
public class ArticleDaoTest extends AbstractWriteDaoTest<Article> {

    /**
     * 
     */
    @Autowired
    private IReadDao<Site> siteReadDao;

    @Override
    Article createEntity() {
        Article article = new Article("Article content");
        article.setOriginalArticleUrl("Original article url");
        article.setSite(siteReadDao.find(1L));
        article.setTitle(new Title("Title"));
        return article;
    }

    @Override
    Article createUpdateEntity() {
        Article article = new Article("Article content2");
        article.setOriginalArticleUrl("Original article url2");
        article.setSite(siteReadDao.find(1L));
        article.setTitle(new Title("Title1"));
        return article;
    }

}
