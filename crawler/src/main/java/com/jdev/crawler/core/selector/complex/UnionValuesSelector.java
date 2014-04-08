/**
 *
 */
package com.jdev.crawler.core.selector.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 */
public class UnionValuesSelector<T> implements ISelector<T> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnionValuesSelector.class);

    /**
     * List of cookies selectors.
     */
    private final List<ISelector<T>> list;

    /**
     * Composite parameter name.
     */
    private final String parameterName;

    /**
     * <b>Please note</b> only Cookie selector and static selectors are
     * accepted.
     */
    public UnionValuesSelector(final String name, final List<ISelector<T>> cookieSelectors) {
        Assert.isTrue(cookieSelectors != null && !cookieSelectors.isEmpty());
        Assert.hasLength(name);
        parameterName = name;
        list = cookieSelectors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.ISelector#select(java.lang.Object)
     */
    @Override
    public Collection<ISelectorResult> select(final T content) throws SelectionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        final StringBuilder sb = new StringBuilder();
        for (final ISelector<T> selector : list) {
            final Collection<ISelectorResult> selectorResultList = selector.select(content);
            for (final ISelectorResult iSelectorResult : selectorResultList) {
                sb.append(iSelectorResult.getValue());
            }
        }
        resultList.add(new SelectorResult(parameterName, sb.toString()));
        LOGGER.debug("Selected name= {} value = {}", parameterName, sb.toString());
        LOGGER.debug("Selected number of records {}", resultList.size());
        return resultList;
    }
}
