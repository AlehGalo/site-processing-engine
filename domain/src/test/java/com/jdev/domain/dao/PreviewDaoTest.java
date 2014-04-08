/**
 * 
 */
package com.jdev.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Preview;
import com.jdev.domain.domain.Site;
import com.jdev.domain.domain.Title;

/**
 * @author Aleh
 * 
 */
public class PreviewDaoTest extends AbstractWriteDaoTest<Preview> {

    /**
     * Article dao service.
     */
    @Autowired
    private IWriteDao<Article> articleDaoService;

    /**
     * Site service.
     */
    @Autowired
    private IReadDao<Site> siteDaoService;

    @Override
    Preview createEntity() {
        Preview preview = new Preview("Preview");
        Title title = new Title("title");
        preview.setTitle(title);
        Article article = new Article("Content");
        article.setTitle(title);
        article.setOriginalArticleUrl("someUrl");
        article.setSite(siteDaoService.find(1L));
        articleDaoService.save(article);
        preview.setArticle(article);
        return preview;
    }

    @Override
    Preview createUpdateEntity() {
        Preview preview = new Preview("Preview1");
        Title title = new Title("title1");
        preview.setTitle(title);
        Article article = new Article("Content1");
        article.setTitle(title);
        article.setOriginalArticleUrl("someUrl1");
        article.setSite(siteDaoService.find(2L));
        articleDaoService.save(article);
        preview.setArticle(article);
        return preview;
    }
}
