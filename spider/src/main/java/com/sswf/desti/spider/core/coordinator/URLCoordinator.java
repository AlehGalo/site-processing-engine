/**
 * 
 */
package com.sswf.desti.spider.core.coordinator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.set.UnmodifiableSet;

import com.sswf.desti.domain.adapter.IContent;

/**
 * @author Aleh
 * 
 *         Synchronised version.
 * 
 */
public class URLCoordinator implements IURLCoordinator<IContent> {

    /**
     * 
     */
    private final Set<IContent> remainingUrls;

    /**
     * 
     */
    private final Set<IContent> walkedUrls;

    /**
     * 
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * 
     */
    public URLCoordinator() {
        remainingUrls = Collections.synchronizedSet(new HashSet<IContent>());
        walkedUrls = Collections.synchronizedSet(new HashSet<IContent>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.spider.core.coordinator.IURLCoordinator#getRemainingUrls()
     */
    @Override
    public UnmodifiableSet getRemainingUrls() {
        return (UnmodifiableSet) UnmodifiableSet.decorate(remainingUrls);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.spider.core.coordinator.IURLCoordinator#next()
     */
    @Override
    public IContent next() {
        if (remainingUrls.isEmpty()) {
            return null;
        }
        final IContent url = (IContent) CollectionUtils.get(remainingUrls, FIRST_ELEMENT_INDEX);
        remainingUrls.remove(url);
        walkedUrls.add(url);
        return url;
    }

    /**
     * @return
     */
    public int getUrlsCount() {
        return walkedUrls.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.spider.core.coordinator.IURLCoordinator#addAllUrls(java
     * .util.Collection)
     */
    @Override
    public <E extends Collection<IContent>> void addAllUrls(final E col) {
        if (CollectionUtils.isNotEmpty(col)) {
            col.removeAll(walkedUrls);
            remainingUrls.addAll(col);
        }
    }
}