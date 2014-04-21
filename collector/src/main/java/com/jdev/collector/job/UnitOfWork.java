/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;

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
    IWriteDao<Article> writeArticleDao;

    @Override
    public void saveArticle(final Article article) {
        writeArticleDao.save(article);
        System.out.println(article.getTitle());
    }

}
