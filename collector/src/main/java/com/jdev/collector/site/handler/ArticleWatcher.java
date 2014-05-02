/**
 * 
 */
package com.jdev.collector.site.handler;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
public class ArticleWatcher implements IObservable, IProcessResultHandler {

    /**
     * 
     */
    private final List<IObserver> listeners;

    /**
     * 
     */
    private Article article;

    /**
     * 
     */
    private final ISelector<String> contentSelector, headerSelector;

    /**
     * @param writeIWriteDao
     * @param site
     */
    public ArticleWatcher(final ISelector<String> contentSelector,
            final ISelector<String> headerSelector) {
        Assert.notNull(contentSelector);
        Assert.notNull(headerSelector);
        listeners = new ArrayList<>();
        this.contentSelector = contentSelector;
        this.headerSelector = headerSelector;
    }

    /**
     * @param listener
     */
    @Override
    public void addListener(final IObserver listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(final IObserver listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void notifyListeners() {
        for (IObserver listener : listeners) {
            listener.articleCollected(article);
        }
    }

    /**
     * @return the article
     */
    protected final Article getArticle() {
        return article;
    }

    /**
     * @param article
     *            the article to set
     */
    protected final void setArticle(final Article article) {
        this.article = article;
    }

    @Override
    public void handle(final IProcessSession session, final IEntity entity) throws CrawlerException {
        String content = new String(entity.getContent(), entity.getCharset());
        Collection<ISelectorResult> contentSelectorResult = contentSelector.select(content);
        Collection<ISelectorResult> titleSelectorResult = headerSelector.select(content);
        if (isNotEmpty(contentSelectorResult) && isNotEmpty(titleSelectorResult)) {
            Article article = new Article(getValueFromTheFirtSelectorResult(contentSelectorResult));
            article.setTitle(getValueFromTheFirtSelectorResult(titleSelectorResult));
            setArticle(article);
            notifyListeners();
        } else {
            // TODO: add processing
        }
    }

    /**
     * @param collection
     *            of value results.
     * @return string value.
     */
    private String getValueFromTheFirtSelectorResult(final Collection<ISelectorResult> collection) {
        return ((ISelectorResult) CollectionUtils.get(collection, 0)).getValue();
    }
}