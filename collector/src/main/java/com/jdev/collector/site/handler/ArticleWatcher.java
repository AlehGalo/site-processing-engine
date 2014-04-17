/**
 * 
 */
package com.jdev.collector.site.handler;

import java.util.ArrayList;
import java.util.List;

import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
public class ArticleWatcher implements IObservable {

    /**
     * 
     */
    private final List<IObserver> listeners;

    /**
     * 
     */
    private Article article;

    /**
     * @param writeIWriteDao
     * @param site
     */
    public ArticleWatcher() {
        listeners = new ArrayList<>();
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
}