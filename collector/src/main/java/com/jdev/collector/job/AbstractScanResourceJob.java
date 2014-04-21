/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jdev.collector.site.CollectorFactory;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
abstract class AbstractScanResourceJob implements IScanResourceJob, IObserver {

    /**
     * 
     */
    @Autowired
    IWriteDao<Article> writeArticleDao;

    /**
     * 
     */
    private final ICollector collector;

    /**
     * @param userName
     */
    public AbstractScanResourceJob(final String userName) {
        Assert.hasLength(userName);
        collector = new CollectorFactory().getCollector(userName, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.job.IScanResourceJob#scan()
     */
    @Override
    @Scheduled(fixedDelay = 3600000, initialDelay = 100)
    public void scan() {
        collector.congregate();
    }

    @Override
    @Transactional
    public void articleCollected(final Article article) {
        writeArticleDao.save(article);
    }
}
