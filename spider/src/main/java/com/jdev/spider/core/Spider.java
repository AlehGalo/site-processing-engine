package com.jdev.spider.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdev.crawler.observer.IObserver;
import com.jdev.crawler.observer.IObserverable;
import com.jdev.domain.Information;
import com.jdev.domain.adapter.ContentUrl;
import com.jdev.domain.adapter.IContent;
import com.jdev.domain.statistics.IStatistics;
import com.jdev.domain.statistics.Statistics;
import com.jdev.spider.core.coordinator.IURLCoordinator;
import com.jdev.spider.core.coordinator.URLCoordinator;

/**
 * @author Aleh
 */
public class Spider implements ISpider, IObserverable<IContent> {

    private final Information information;
    protected final Set<IContent> setOfNotDomainedUrls;
    protected final Statistics statistics;
    private final IURLCoordinator<IContent> urlCoordinator;
    protected final String host;
    private final Set<IObserver<IContent>> observer;
    private int pagesScanned;
    private int urlDepth = -1;

    public Spider(final Information inf) {
        information = inf;
        pagesScanned = 0;
        statistics = new Statistics();
        setOfNotDomainedUrls = new HashSet<>();
        urlCoordinator = new URLCoordinator();
        urlCoordinator.addAllUrls(Collections.singleton((IContent) new ContentUrl(
                inf.getHomeUrl().toString(), null, null)));
        host = information.getHomeUrl().getProtocol() + ":" + "//" + information.getHomeUrl().getHost();
        observer = new CopyOnWriteArraySet<>();
    }

    public Spider(final Information inf, final int maxUrlDepth) {
        this(inf);
        this.urlDepth = maxUrlDepth;
    }

    @Override
    public void collect() {
        collect(Integer.MAX_VALUE);
    }

    @Override
    public void collect(final int pagesLimit) {
        int counter = 0;
        statistics.captureStart();

        while (pagesLimit > counter) {
            IContent url = urlCoordinator.next();
            if (url == null) {
                break;
            }

            int countKeeper = pagesScanned;
            AtomicInteger intVal = new AtomicInteger(pagesScanned);

            OneContentJob job = buildContentJob(intVal);
            job.atomicContentExecution(url);

            pagesScanned = intVal.get();
            if (pagesScanned > countKeeper) {
                ++counter;
            }
        }

        statistics.captureEnd();
    }

    private OneContentJob buildContentJob(AtomicInteger intVal) {
        if (urlDepth != -1) {
            return new OneContentJob(statistics, intVal, host, this, urlCoordinator, setOfNotDomainedUrls,
                    urlDepth);
        } else {
            return new OneContentJob(statistics, intVal, host, this, urlCoordinator, setOfNotDomainedUrls);
        }
    }

    @Override
    public boolean addListener(IObserver<IContent> listener) {
        if (listener != null) {
            return observer.add(listener);
        }
        return false;
    }

    @Override
    public boolean deleteListener(IObserver<IContent> listener) {
        return observer.remove(listener);
    }

    @Override
    public void notifyListeners(final IContent event) {
        if (event == null) {
            return;
        }

        for (IObserver<IContent> element : observer) {
            element.onAction(event);
        }
    }

    public int getPagesScanned() {
        return pagesScanned;
    }

    final IURLCoordinator<IContent> getUrlCoordinator() {
        return urlCoordinator;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    @Override
    public IStatistics getStatistics() {
        return statistics;
    }

    public Set<IContent> getSetOfNotDomainedUrls() {
        return Collections.unmodifiableSet(setOfNotDomainedUrls);
    }
    
}