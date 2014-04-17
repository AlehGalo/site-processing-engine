/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jdev.collector.site.CollectorFactory;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
@Component
public class FlRuJob implements IScanResourceJob, IObserver {

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
     * 
     */
    public FlRuJob() {
        collector = new CollectorFactory().getCollector("informer-fl-ru");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.job.IScanResourceJob#scan()
     */
    @Override
    @Scheduled(fixedDelay = 3600000)
    public void scan() {
        collector.congregate();
    }

    @Override
    public void articleCollected(final Article article) {
        writeArticleDao.save(article);
    }
}