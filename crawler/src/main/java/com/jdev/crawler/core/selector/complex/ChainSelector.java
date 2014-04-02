/**
 * 
 */
package com.jdev.crawler.core.selector.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class ChainSelector<T> implements ISelector<T> {

    /**
     * Set of selectors.
     */
    private final Set<ISelector<T>> setOfSelector;

    /**
     * @param selectors
     */
    @SafeVarargs
    public ChainSelector(final ISelector<T>... selectors) {
        setOfSelector = new HashSet<>();
        if (selectors != null) {
            setOfSelector.addAll(Arrays.asList(selectors));
        }
    }

    @Override
    public Collection<ISelectorResult> select(final T content) throws SelectionException {
        Assert.notNull(content);
        List<ISelectorResult> list = new ArrayList<>();
        for (ISelector<T> selector : setOfSelector) {
            list.addAll(selector.select(content));
        }
        return list;
    }
}
