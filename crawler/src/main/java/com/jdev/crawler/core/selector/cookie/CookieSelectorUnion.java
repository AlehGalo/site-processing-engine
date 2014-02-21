/**
 *
 */
package com.jdev.crawler.core.selector.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.log4j.Logger;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.CookieSelectionException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 */
public class CookieSelectorUnion implements ISelector<CookieStore> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CookieSelectorUnion.class);

    /**
     * List of cookies selectors.
     */
    private final List<ISelector<CookieStore>> list;

    /**
     * Composite parameter name.
     */
    private final String parameterName;

    /**
     * <b>Please note</b> only Cookie selector and static selectors are
     * accepted.
     */
    public CookieSelectorUnion(final String name, final List<ISelector<CookieStore>> cookieSelectors) {
        Assert.isTrue(cookieSelectors != null && !cookieSelectors.isEmpty());
        Assert.hasLength(name);
        parameterName = name;
        list = cookieSelectors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.crawler.core.conf.selector.ISelector#selectValues(java.lang.
     * Object )
     */
    @Override
    public Collection<ISelectorResult> select(final CookieStore content)
            throws SelectionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        final StringBuilder sb = new StringBuilder();
        for (final ISelector<CookieStore> selector : list) {
            final Collection<ISelectorResult> selectorResultList = selector.select(content);
            for (final ISelectorResult iSelectorResult : selectorResultList) {
                sb.append(iSelectorResult.getValue());
            }
        }
        resultList.add(new SelectorResult(parameterName, sb.toString()));
        if (resultList.isEmpty()) {
            LOGGER.error("No data selected.");
            throw new CookieSelectionException(parameterName);
        }
        return resultList;
    }
}
