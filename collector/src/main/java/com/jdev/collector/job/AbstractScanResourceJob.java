/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.jdev.collector.site.AbstractCollector;
import com.jdev.collector.site.ICollector;
import com.jdev.collector.site.handler.IObserver;
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
    private IUnitOfWork unitOfWork;
    /**
     * 
     */
    private final ICollector collector;

    /**
     * @param userName
     */
    public AbstractScanResourceJob(final AbstractCollector collector) {
        Assert.notNull(collector);
        this.collector = collector;
        collector.setEventHandlerDelegate(this);
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
    public void articleCollected(final Article article) {
        unitOfWork.saveArticle(article);
    }
}
