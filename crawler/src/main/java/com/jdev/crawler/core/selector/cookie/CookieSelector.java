/**
 *
 */
package com.jdev.crawler.core.selector.cookie;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.CookieSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 */
public class CookieSelector implements ISelector<CookieStore> {

    /**
     * Logger for the main actions.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieSelector.class);

    /**
     * Name of the property.
     */
    private final String name;

    /**
     * @param name
     */
    public CookieSelector(final String name) {
        Assert.hasLength(name);
        this.name = name;
    }

    /**
     * @param selectUnit
     */
    public CookieSelector(final ISelectUnit selectUnit) {
        Assert.notNull(selectUnit);
        Assert.hasLength(selectUnit.getName());
        this.name = selectUnit.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.dre.bm.crawler.base.crawler.conf.selector.ISelector#selectValues
     * (java.lang.String)
     */
    @Override
    public List<ISelectorResult> select(final CookieStore cookStore)
            throws CookieSelectionException {
        if (cookStore == null) {
            throw new CookieSelectionException("Cookie store for cookie selector cannot be null");
        }
        final List<ISelectorResult> list = new ArrayList<ISelectorResult>();
        for (final Cookie cookie : cookStore.getCookies()) {
            if (name.equals(cookie.getName())) {
                final String value = cookie.getValue();
                if (isEmpty(value)) {
                    throw CookieSelectionException.createFormatted(name);
                }
                LOGGER.debug("Properties extracted {} {}", name, value);
                list.add(new SelectorResult(name, value));
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Results found {}", list.size());
        }
        return list;
    }
}