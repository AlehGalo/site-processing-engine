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
        return EntityUtils.createArticleWithDependencies("Article content", "Reason1", siteDao,
                credentialDao, jobDao);
    }

    @Override
    Article createUpdateEntity() {
        return EntityUtils.createArticleWithDependencies("Article content2", "Reason2", siteDao,
                credentialDao, jobDao);
    }

}
