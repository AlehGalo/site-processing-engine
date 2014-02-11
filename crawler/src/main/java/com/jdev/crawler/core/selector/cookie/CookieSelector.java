/**
 *
 */
package com.jdev.crawler.core.selector.cookie;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     *
     */
    private final String name;

    /**
     * @param name
     */
    public CookieSelector(final String name) {
        Assert.hasLength(name);
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.dre.bm.crawler.base.crawler.conf.selector.ISelector#selectValues
     * (java.lang.String)
     */
    @Override
    public List<ISelectorResult> selectValues(final CookieStore cookStore)
            throws CookieSelectionException {
        final List<ISelectorResult> list = new ArrayList<ISelectorResult>();
        for (final Cookie cookie : cookStore.getCookies()) {
            if (name.equals(cookie.getName())) {
                final String value = cookie.getValue();
                if (StringUtils.isEmpty(value)) {
                    throw new CookieSelectionException(name);
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("[CookieSelector] >> {} {}", name, value);
                }
                list.add(new SelectorResult(name, value));
            }
        }
        if (list.isEmpty()) {
            LOGGER.error("No values selected.");
            throw new CookieSelectionException(name);
        }
        return list;
    }
}